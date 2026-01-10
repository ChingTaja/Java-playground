// ch35 - 40
package Base;

public class FirstClass {
    // shortcut - psvm
    public static void main(String[] args) {
        System.out.print("Hello world");
        boolean isAlien = true;
        if (isAlien == true) {
            System.out.print("I'm alien");
        }

        int topScore = 100;
        if (topScore != 100) {
            System.out.print("You got the hight score!");
        }
        
        int secondScore = 60;
        if(topScore > secondScore && topScore < 100)
        {
            System.out.print("Greater than second top score and less than 100!");
        }

        if ((topScore > 90) || secondScore <= 90) {
            System.out.print("Either or both of the conditions are true");
        }

        boolean isCar = false;
        if (isCar == true) {
            // if (isCar) {
            // if (!isCar) {
            System.out.print("This is not supposed to happen");
        }
        
        // ch41 ternary operator
        String makeOfcar = "Volkswagen";
        boolean isDomstic = makeOfcar == "Volkswagen" ? false : true;

        if (isDomstic) {
            System.out.println("The car is domestic");
        }

        //ch42
        // google the keyword "Java operator precedence"
        double myFirstValue = 20.00d;
        double mySecondValue = 80.00d;
        double myValueTotal = myFirstValue + mySecondValue * 100.00d;
        System.out.println("myValueTotal = " + myValueTotal);
        double theRemainder = myValueTotal % 40.00d;
        System.out.println("theReminder = " + theRemainder);
        boolean isNoRemainer = (theRemainder == 0) ? true : false;
        System.out.println("isNoRemainer = " + isNoRemainer);
        if (!isNoRemainer) {
            System.out.println("got some remainder");

}
    }
}
