package info.fa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rule {

    //product can be of multiple letters
    protected String product;
    protected int standardPrice;

    public int getTotal(Integer count) {
        return count * standardPrice;
    };
}
