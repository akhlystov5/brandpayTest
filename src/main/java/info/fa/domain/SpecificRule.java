package info.fa.domain;

/** represents 3 for 130, 2 for 45*/
public class SpecificRule extends Rule {

    private final int groupingNumber;
    private final int groupingPrice;

    public SpecificRule(String product, int standardPrice, int groupingNumber, int groupingPrice) {
        super(product, standardPrice);
        this.groupingNumber = groupingNumber;
        this.groupingPrice = groupingPrice;

    }

    public int getTotal(Integer count) {
        return (count / groupingNumber * groupingPrice) + super.getTotal(count % groupingNumber);
    };
}
