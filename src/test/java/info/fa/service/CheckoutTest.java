package info.fa.service;

import info.fa.domain.Rule;
import info.fa.domain.SpecificRule;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckoutTest {

    List<Rule> rules = new ArrayList<>();
    Checkout checkout;

    @Test
    void testTotal() {
        assertEquals(0, price(""));
        assertEquals( 50, price("A"));
        assertEquals( 80, price("AB"));
        assertEquals(115, price("CDBA"));

        assertEquals(100, price("AA"));
        assertEquals(130, price("AAA"));
        assertEquals(180, price("AAAA"));
        assertEquals(230, price("AAAAA"));
        assertEquals(260, price("AAAAAA"));

        assertEquals(160, price("AAAB"));
        assertEquals(175, price("AAABB"));
        assertEquals(190, price("AAABBD"));
        assertEquals(190, price("DABABA"));
    }

    @Test
    void testIncremental() {
        assertEquals(0, checkout.geTotalPrice());
        checkout.scan("A");
        assertEquals(50, checkout.geTotalPrice());
        checkout.scan("B");
        assertEquals(80, checkout.geTotalPrice());
        checkout.scan("A");
        assertEquals(130, checkout.geTotalPrice());
        checkout.scan("A");
        assertEquals(160, checkout.geTotalPrice());
        checkout.scan("B");
        assertEquals(175, checkout.geTotalPrice());
    }

    private int price(String singleLetterProducts) {
        checkout.scan(singleLetterProducts);
        int totalPrice = checkout.geTotalPrice();
        checkout.clearProducts();

        return totalPrice;
    }

    @BeforeAll
    void setupRules() {
        rules.add(new SpecificRule("A", 50, 3, 130));
        rules.add(new SpecificRule("B", 30,2, 45));
        rules.add(new Rule("C", 20));
        rules.add(new Rule("D", 15));

        rules.add(new Rule("Carrot", 20));

        checkout = new Checkout(rules);
    }

    @AfterEach
    void tearDown() {
        checkout.clearProducts();
    }



    @Test
    void testProduct1items1() {
        checkout.scan("A");
        assertEquals(50, checkout.geTotalPrice());
    }

    @Test
    void testProduct1items5normal() {
        checkout.scan("CCCCC");
        assertEquals(100, checkout.geTotalPrice());
    }

    @Test
    void testProductRealName1items5normal() {
        checkout.add("Carrot");
        checkout.add("Carrot");
        checkout.add("Carrot");
        checkout.add("Carrot");
        checkout.add("Carrot");

        assertEquals(100, checkout.geTotalPrice());
    }

    @Test
    void testProduct1items5clever() {
        checkout.scan("AAAAA");
        assertEquals(230, checkout.geTotalPrice());
    }

}