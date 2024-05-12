package org.kst.storemgmtbackend;

import lombok.RequiredArgsConstructor;
import org.kst.storemgmtbackend.models.*;
import org.kst.storemgmtbackend.repositories.ItemRepository;
import org.kst.storemgmtbackend.repositories.RatingRepository;
import org.kst.storemgmtbackend.repositories.StoreRepository;
import org.kst.storemgmtbackend.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class StoreMgmtBackendApplication {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final RatingRepository ratingRepository;

    public static void main(String[] args) {
        SpringApplication.run(StoreMgmtBackendApplication.class, args);
    }

    //    @Override
    public void run(String... args) throws Exception {
        // Create and save users
        User user1 = User.builder()
                .username("user1")
                .password("password1")
                .email("user1@example.com")
                .phoneNumber("123456789")
                .profile(Profile.builder().firstname("John").lastname("Doe").build())
                .lastLogin(LocalDateTime.now())
                .isEnabled(true)
                .build();
        userRepository.save(user1);

        // Create and save stores
        Store store1 = Store.builder()
                .description("Store 1")
                .remark("Remark for Store 1")
                .contactDetails(ContactDetails.builder()
                        .contactEmail(new HashSet<>(List.of("store1@example.com")))
                        .phoneNumber(new HashSet<>(List.of("987654321")))
                        .build())
                .geolocation(Geolocation.builder().latitude("123").longitude("456").build())
                .address(Address.builder().street("Street 1").city("City 1").state("State 1").zipcode("12345").country("Country 1").remark("Remark for Address 1").build())
                .approvedDate(LocalDateTime.now())
                .isApproved(true)
                .createdAt(LocalDateTime.now())
                .owners(new HashSet<>(List.of(user1)))
                .build();
        storeRepository.save(store1);

        // Create and save items
        Item item1 = Item.builder()
                .shortDescription("Item 1")
                .longDescription("Long description for Item 1")
                .price(10.0f)
                .availableUnit(100)
                .imageUrlList(Arrays.asList("image1.jpg", "image2.jpg"))
                .category(new ArrayList<>())
                .build();
        itemRepository.save(item1);

        // Create and save ratings
        Rating rating1 = Rating.builder()
                .rating(5)
                .comment("Good item")
                .user(user1)
                .item(item1)
                .build();
        ratingRepository.save(rating1);
    }
}
