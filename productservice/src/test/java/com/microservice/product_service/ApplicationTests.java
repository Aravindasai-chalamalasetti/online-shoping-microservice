package com.microservice.product_service;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
	
//	@Container
//	static MongoDBContainer mongoDB = new MongoDBContainer("mongo:4.4.2");
//
//	@Autowired
//	private MockMvc mock;
//
//	@Autowired
//	private ObjectMapper maper;
//
//	@Autowired
//	private ProductDatabase repo;
//
//	@DynamicPropertySource
//	static void setApplicationProperties(DynamicPropertyRegistry dynamic) {
//		dynamic.add("spring.data.mongodb.uri", mongoDB::getReplicaSetUrl);
//	}
	
//	@Test
//	void addProduct() throws Exception {
//		ProductDTO prod = getProductData();
//		String s = maper.writeValueAsString(prod);
//		mock.perform(MockMvcRequestBuilders.post("/product/addProduct")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(s))
//				.andExpect(status().isCreated());
//		Assertions.assertTrue(repo.findAll().size() == 1);
//		Assertions.assertEquals(1, repo.findAll().size());
//	}
//
//	private ProductDTO getProductData() {
//		return ProductDTO.builder()
//				.productName("Iphone 17")
//				.processor("A17 Bionic-chip")
//				.price(BigDecimal.valueOf(79999.99))
//				.build();
//	}
    @Test
	void contextLoads(){

	}

}
