import java.nio.file.attribute.FileTime;

public class Main {
    
    public static void main(String[] args) {

        /*********INITIALISATION DU JEU DE DONNÉES***/

        String coffeeArray [] = {"Expresso", "Ristretto", "Lungo", "Cappucino", "Viennois", "Mocha", "Macchiato"};
        String countryArray [] = {"Columbia", "Brazil", "Ethiopia", "Ivory Coast", "Java", "Vietnam"," "};


        Bloom coffeeFilter = new Bloom(20);
        Bloom countryFilter = new Bloom(20);


        /*********INSERTION DES DONNÉES**********/

        coffeeFilter.fill(coffeeArray);
        countryFilter.fill(countryArray);

        /*********VERIFICATION DE L'APPARTENANCE******/

        coffeeFilter.isInto("roger");
        countryFilter.isInto("Java");

        /*********UNION & INTERSECTION********/

        System.out.println("\n\nBloom input : \n");

        coffeeFilter.display();
        countryFilter.display();


        Bloom inter = coffeeFilter.bloomIntersection( countryFilter,coffeeFilter);
        Bloom union = coffeeFilter.bloomUnion(coffeeFilter, countryFilter);


        System.out.println("\nOutput Union:");
        union.display();

        System.out.println("\n Output Intersection");
        inter.display();


    }


}
