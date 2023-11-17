package refrigerator.back.global.common;

public class NumberDto {

    private final Long number;

    public NumberDto(Long number) {
        this.number = number;
    }

    public int getNumber(){
        if (number == null){
            return 0;
        }
        return number.intValue();
    }

}
