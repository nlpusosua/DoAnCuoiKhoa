package com.example.PhongTroOnline;

import com.example.PhongTroOnline.entity.*;
import com.example.PhongTroOnline.model.enums.Gender;
import com.example.PhongTroOnline.model.enums.PriceType;
import com.example.PhongTroOnline.model.enums.TransactionStatus;
import com.example.PhongTroOnline.model.enums.UserRole;
import com.example.PhongTroOnline.repository.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.slugify.Slugify;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class PhongTroOnlineApplicationTests {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private DepositHistoryRepository depositHistoryRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private FavoriteRepository favoriteRepository;
	@Autowired
	private ImageRoomRepository imageRoomRepository;
	@Autowired
	private NewsRepository newsRepository;
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private ServicePriceRepository servicePriceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VideoRoomRepository videoRoomRepository;
	@Autowired
	private WardRepository wardRepository;


	@Test
	void save_categories() {
		Faker faker = new Faker();
		Slugify slugify = Slugify.builder().build();
		for (int i = 0; i < 100; i++) {
			String name = faker.commerce().department();
			String title = faker.book().title();
			String description = Optional.ofNullable(faker.lorem().paragraph()).orElse("Default description");
			Category category = Category.builder()
					.name(name)
					.slug(slugify.slugify(name))
					.title(title)
					.description(description)
					.build();
			categoryRepository.save(category);
		}
	}
	@Test
	void save_wards() {
		Faker faker = new Faker();
		for (int i = 0; i < 100; i++) {
			String name = faker.address().streetName(); // Fake tên phường
			Ward ward = new Ward();
			ward.setName(name);
			wardRepository.save(ward);
		}
	}
	@Test
	void save_districts() {
		Faker faker = new Faker();
		for (int i = 0; i < 100; i++) {
			String name = faker.address().streetName(); // Fake tên quận
			District district = new District();
			district.setName(name);
			districtRepository.save(district);
		}
	}
	@Test
	void save_provinces() {
		Faker faker = new Faker();
		for (int i = 0; i < 100; i++) {
			String name = faker.address().cityName(); // Fake tên tỉnh/thành phố
			Province province = new Province();
			province.setName(name);
			provinceRepository.save(province);
		}
	}
	@Test
	void save_image_rooms() {
		Faker faker = new Faker();
		for (int i = 0; i < 100; i++) {
			String imgUrl = faker.internet().image(); // Fake URL của hình ảnh
			ImageRoom imageRoom = new ImageRoom();
			imageRoom.setImgUrl(imgUrl);
			imageRoomRepository.save(imageRoom);
		}
	}
	@Test
	void save_video_rooms() {
		Faker faker = new Faker();
		for (int i = 0; i < 100; i++) {
			String videoUrl = faker.internet().url(); // Fake URL của video

			VideoRoom videoRoom = new VideoRoom();
			videoRoom.setVideoUrl(videoUrl);

			videoRoomRepository.save(videoRoom);
		}
	}
	@Test
	void save_users() {
		Faker faker = new Faker();
		for (int i = 0; i < 50; i++) {
			String name = faker.name().fullName();
			User user = User.builder()
					.name(name)
					.email(faker.internet().emailAddress())
					.avatar("https://placehold.co/600x400?text=" + String.valueOf(name.charAt(0)).toUpperCase()) // Fake avatar URL với chữ cái đầu của tên
					.password("123") // Mật khẩu cố định là "123"
					.role(i == 0 || i == 1 ? UserRole.ADMIN : UserRole.USER) // Nếu là user đầu tiên hoặc thứ hai, đặt vai trò là ADMIN, ngược lại là USER
					.account_balance(faker.number().numberBetween(0, 10000))
					.createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now())
					.build();
			userRepository.save(user);
		}
	}
	@Test
	void save_services() {
		Faker faker = new Faker();

		for (int i = 0; i < 50; i++) {
			String name = faker.commerce().productName();
			String description = faker.lorem().sentence();
			String titleColor = faker.color().hex();  // Mã màu hex
			boolean autoApprove = faker.bool().bool();
			boolean showPhone = faker.bool().bool();

			Service service = Service.builder()
					.name(name)
					.description(description)
					.titleColor(titleColor)
					.autoApprove(autoApprove)
					.showPhone(showPhone)
					.build();
			serviceRepository.save(service);
		}
	}
	@Test
	void save_servicePrices() {
		Faker faker = new Faker();
		Random random = new Random();
		List<Service> services = serviceRepository.findAll();

		for (int i = 0; i < 50; i++) {
			PriceType priceType = PriceType.values()[random.nextInt(PriceType.values().length)];
			int price = faker.number().numberBetween(100, 10000);
			Integer discountPrice = random.nextBoolean() ? faker.number().numberBetween(50, price) : null;
			Service randomService = services.get(random.nextInt(services.size()));

			ServicePrice servicePrice = ServicePrice.builder()
					.type(priceType)
					.price(price)
					.discountPrice(discountPrice)
					.service(randomService)
					.minDays(3)
					.isFlexible(false)
					.build();
			servicePriceRepository.save(servicePrice);
		}
	}
	@Test
	void save_rooms() {
		Faker faker = new Faker();
		Random random = new Random();
		Slugify slugify = Slugify.builder().build();

		List<User> users = userRepository.findAll();
		List<Category> categories = categoryRepository.findAll();
		List<Service> services = serviceRepository.findAll();
		List<Province> provinces = provinceRepository.findAll();
		List<District> districts = districtRepository.findAll();
		List<Ward> wards = wardRepository.findAll();
		List<ImageRoom> imageRooms = imageRoomRepository.findAll();
		List<VideoRoom> videoRooms = videoRoomRepository.findAll();

		for (int i = 0; i < 100; i++) {
			User user = users.get(random.nextInt(users.size()));
			Category category = categories.get(random.nextInt(categories.size()));
			Service service = services.get(random.nextInt(services.size()));
			Province province = provinces.get(random.nextInt(provinces.size()));
			District district = districts.get(random.nextInt(districts.size()));
			Ward ward = wards.get(random.nextInt(wards.size()));

			List<ImageRoom> selectedImageRooms = new ArrayList<>();
			for (int j = 0; j < random.nextInt(6) + 5; j++) {
				ImageRoom imageRoom = imageRooms.get(random.nextInt(imageRooms.size()));
				if (!selectedImageRooms.contains(imageRoom)) {
					selectedImageRooms.add(imageRoom);
				}
			}

			List<VideoRoom> selectedVideoRooms = new ArrayList<>();
			for (int j = 0; j < random.nextInt(3) + 1; j++) {
				VideoRoom videoRoom = videoRooms.get(random.nextInt(videoRooms.size()));
				if (!selectedVideoRooms.contains(videoRoom)) {
					selectedVideoRooms.add(videoRoom);
				}
			}

			String title = faker.book().title();
			String slug = slugify.slugify(title);

			// Check for duplicate slug and append a suffix if needed
			int suffix = 1;
			while (roomRepository.existsBySlug(slug)) {
				slug = slugify.slugify(title) + "-" + suffix++;
			}

			Room room = Room.builder()
					.title(title)
					.slug(slug)
					.description(faker.lorem().paragraph(2)) // Giới hạn độ dài
					.gender(Gender.values()[random.nextInt(Gender.values().length)])
					.price(faker.number().numberBetween(100000, 10000000))
					.areas(faker.number().numberBetween(15, 150))
					.createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now())
					.expiresAt(LocalDateTime.now().plusMonths(1))
					.status(random.nextBoolean())
					.map("https://maps.google.com/?q=" + faker.address().latitude() + "," + faker.address().longitude())
					.streetDetail(faker.address().streetAddress())
					.user(user)
					.category(category)
					.services(service)
					.provinces(province)
					.district(district)
					.ward(ward)
					.build();

			roomRepository.save(room);
		}
	}
	@Test
	void save_news() {
		Random random = new Random();
		Faker faker = new Faker();
		Slugify slugify = Slugify.builder().build();

		// Lấy danh sách người dùng từ repository
		List<User> users = userRepository.findAll();

		for (int i = 0; i < 100; i++) {
			// Random một người dùng
			User user = users.get(random.nextInt(users.size()));

			// Tạo tiêu đề và slug
			String title = faker.book().title();
			String baseSlug = slugify.slugify(title);
			String uniqueSlug = baseSlug;

			// Thêm UUID để đảm bảo tính duy nhất
			while (newsRepository.existsBySlug(uniqueSlug)) {
				uniqueSlug = baseSlug + "-" + UUID.randomUUID().toString().substring(0, 8);
			}

			News news = News.builder()
					.title(title)
					.slug(uniqueSlug)
					.description(faker.lorem().paragraph())
					.content(faker.lorem().sentence())
					.thumbnail("https://placehold.co/600x400?text=" + String.valueOf(title.charAt(0)).toUpperCase())
					.status(faker.bool().bool())
					.createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now())
					.user(user)
					.build();

			// Lưu đối tượng News vào cơ sở dữ liệu
			try {
				newsRepository.save(news);
			} catch (DataIntegrityViolationException e) {
				// Xử lý nếu trùng lặp vẫn xảy ra
				System.out.println("Duplicate slug detected: " + uniqueSlug);
			}
		}
	}
	@Test
	void save_favorites() {
		Random random = new Random();
		Faker faker = new Faker();

		// Lấy danh sách người dùng và phòng từ repository
		List<User> users = userRepository.findAll();
		List<Room> rooms = roomRepository.findAll();

		for (int i = 0; i < 100; i++) {
			// Random một người dùng
			User user = users.get(random.nextInt(users.size()));

			// Random một phòng
			Room room = rooms.get(random.nextInt(rooms.size()));

			// Tạo đối tượng Favorite
			Favorite favorite = Favorite.builder()
					.user(user)
					.room(room)
					.createdAt(LocalDateTime.now())
					.build();

			// Lưu đối tượng Favorite vào cơ sở dữ liệu
			favoriteRepository.save(favorite);
		}
	}
	@Test
	void save_deposit_histories() {
		Random random = new Random();
		Faker faker = new Faker();

		// Lấy danh sách người dùng và dịch vụ từ repository
		List<User> users = userRepository.findAll();
		List<Service> services = serviceRepository.findAll();
		List<TransactionStatus> statuses = Arrays.asList(TransactionStatus.values());

		for (int i = 0; i < 100; i++) {
			// Random một người dùng
			User user = users.get(random.nextInt(users.size()));
			// Random một dịch vụ
			Service service = services.get(random.nextInt(services.size()));
			// Random trạng thái giao dịch
			TransactionStatus status = statuses.get(random.nextInt(statuses.size()));
			// Tạo đối tượng DepositHistory
			DepositHistory depositHistory = DepositHistory.builder()
					.createdAt(LocalDateTime.now())
					.depositForm(faker.lorem().word()) // Tạo dữ liệu giả cho phương thức gửi tiền
					.amount(faker.number().numberBetween(1000, 100000)) // Tạo số tiền ngẫu nhiên
					.user(user)
					.status(status)
					.service(service)
					.build();
			// Lưu đối tượng DepositHistory vào cơ sở dữ liệu
			depositHistoryRepository.save(depositHistory);
		}
	}

	@Test
	void test_pagination() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<Room> page = roomRepository.findByStatus(true, pageRequest);

		System.out.println("Total pages: " + page.getTotalPages());
		System.out.println("Total elements: " + page.getTotalElements());
		page.getContent().forEach(m -> System.out.println(m.getId()));
	}




















}
