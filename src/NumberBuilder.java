import java.math.BigDecimal;

public class NumberBuilder {
    private StringBuilder number;

    public NumberBuilder(StringBuilder number) {
        this.number = number;
    }

    public BigDecimal createNumber(){
        if (number != null){
            String copy = number.toString();
            number.setLength(0);
            return new BigDecimal(copy);
        }
        return null;
    }
    public void put(Character ch){
        number.append(ch);
    }
}
