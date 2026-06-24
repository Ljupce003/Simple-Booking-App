package finki.emt.lab_emt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

//import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LabEmtApplicationTests {

    interface Calculate {
        int sum(int a,int b);
        int minus(int a,int b);
        double divide(int a,int b);
        double multiply(int a,int b);

    }

    static class Calculator{
        private Calculate calculate;

        public Calculator(Calculate calculate) {
            this.calculate = calculate;
        }

        public int Sum(int a,int b){
            return calculate.sum(a,b);
        }

        public int Minus(int a,int b){
            return calculate.minus(a,b);
        }

        public double Divide(int a,int b){
            return calculate.divide(a,b);
        }

        public double Multiply(int a,int b){
            return calculate.multiply(a,b);
        }

    }

    @Mock Calculate calculate;


    @Spy @InjectMocks Calculator calculatorSpy;



    @Test
    public void testMockingInterface(){





        when(calculate.sum(anyInt(),anyInt())).thenReturn(10);
        when(calculate.sum(5,10)).thenReturn(15);
        when(calculate.minus(5,10)).thenReturn(-5);
        when(calculate.multiply(5,10)).thenReturn(50.0);
//        when(calculate.divide(5,10)).thenReturn(0.5);


        when(calculatorSpy.Divide(anyInt(),anyInt())).thenReturn(1000.0);

        assertEquals(15, calculatorSpy.Sum(5,10));
        assertEquals(10, calculatorSpy.Sum(7,17));
        assertEquals(-5, calculatorSpy.Minus(5,10));
        assertEquals(50.0, calculatorSpy.Multiply(5,10));
        assertEquals(  1000.0, calculatorSpy.Divide(5,10));



    }

}
