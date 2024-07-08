package info.fa.service;

import com.google.common.base.Splitter;
import info.fa.domain.Rule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Data
public class Checkout {

    //TODO not sure we need this list to keep the original order?
    private final List<String> products = new ArrayList<>();

    private final Map<String, Integer> itemsPerProduct = new HashMap<>();

    private final Map<String, Rule> rules = new HashMap<>();

    public Checkout(List<Rule> rulesList) {
        rulesList.forEach(rule -> {
            rules.put(rule.getProduct(), rule);
        });
    }

    public int geTotalPrice() {
        if (itemsPerProduct.isEmpty()) {
            return 0;
        }
        //TODO can be improved. This is not optimal as recalculated for every call. It can be cashed
        int totalPrice = itemsPerProduct.keySet().stream().mapToInt( product -> {
            return rules.get(product).getTotal(itemsPerProduct.get(product));
        }).sum();
        return totalPrice;
    }

    public void scan(String product) {
        if (product.isBlank()) {
            return;
        }
        Iterable<String> iterator = Splitter.fixedLength(1).split(product);
        iterator.forEach(this::add);
    }

    public void add(String product) {
        products.add(product);
        if ( ! itemsPerProduct.containsKey(product)) {
            itemsPerProduct.put(product, 0);
        }
        itemsPerProduct.replace(product, itemsPerProduct.get(product) + 1);
    }


    public void clearProducts() {
        products.clear();
        itemsPerProduct.clear();
    }
}
