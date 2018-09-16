package Calculator;
import java.util.*;
public class Num {

    public enum numTypes{PRIME, NATURAL, WHOLE, INTEGER, RATIONAL, IRRATIONAL, REAL, IMAGINARY, COMPLEX}
    private Double realNum; //the real part's numerator
    private Double realDen; //the real part's denominator
    private Double imagNum; //the imaginary part's numerator
    private Double imagDen; //the imaginary part's numerator

    public Num(Double real, Double imag){
        this.realNum = real;
        this.realDen = (double)1;
        this.imagNum = imag;
        this.imagDen = (double)1;
    }

    public Num(Double realNum, Double realDen, Double imagNum, Double imagDen){
        if(realDen*imagDen==0) throw new ArithmeticException("Divide by zero error.");
        else{
            this.realNum = realNum;
            this.realDen = realDen;
            this.imagNum = imagNum;
            this.imagDen = imagDen;
        }
    }

    public ArrayList<numTypes> getGenus(){
        ArrayList<numTypes> genus = new ArrayList<>();
        genus.add(numTypes.COMPLEX);
        if(realNum==0 && imagNum!=0) genus.add(numTypes.IMAGINARY);
        else if(imagNum==0){
            genus.add(numTypes.REAL);
            if((realNum/realDen)%1==0){
                genus.add(numTypes.RATIONAL);
                genus.add(numTypes.INTEGER);
                if(realNum/realDen>=0){
                    genus.add(numTypes.WHOLE);
                    if(realNum!=0){
                        genus.add(numTypes.NATURAL);
                        if(isPrime((int)(realNum/realDen))) genus.add(numTypes.PRIME);
                    }
                }
            }else if(realNum%1==0 && realDen%1==0) genus.add(numTypes.RATIONAL);
        }
        return genus;
    }

    public boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) if (n % i == 0) return false;
        return true;
    }

    public Double getRealPart(){
        return realNum/realDen;
    }

    public Double getImagPart(){
        return imagNum/imagDen;
    }

    public Double getRealNum(){
        return realNum;
    }

    public Double getRealDen(){
        return realDen;
    }

    public Double getImagNum(){
        return imagNum;
    }

    public Double getImagDen(){
        return imagDen;
    }
}