import java.util.Arrays;

public class Bloom {

    /***********ATRIBUTE**************/

    private boolean[] bloom;
    private int bloomSize;

    /***********BUILDER***************/

    Bloom (int size){

        bloomSize = size ;
        bloom = new boolean [bloomSize];//augmenter la longueur du filtre permet d'eviter d'avoir une probabilité trop importante  de "faux-positifs". en changeant le coefficient on peut voir la probabilité de faux-positif évoluer.
        Arrays.fill(bloom,false);
    }

    /***********METHODS***************/

    /** hashOne se base sur la fonction de hachage disponible en natif dans java, on utilise le modulo pour obtenir un
     * index possible dans le filtre que l'on vient de créer. */
    private int hashOne (String value){

        int hash = (value.hashCode() ) % this.bloom.length;

        hash = (hash<0)? 0-hash : hash;

        return hash;

    }


    /** hashTwo repose sur la méthode de la multiplication expliquée plus en détail dans le rapport , ici A est une
     * constante choisie au hazard. */
    private int hashTwo (String value){

        int K = convert(value);
        double A = 0.324;
        int M = this.bloom.length;

        return (int) (M * ( K * A % 1));


    }


    /** insertValue va calculer les hashCodes de la valeur en paraètre, ces hashCodes seront ensuite utiliser comme indice pour determiné
     * quelle celule du filtre seront passer à true. */
    private void insertValue (String value){

        int i = hashOne(value);
        int j = hashTwo(value);

        this.bloom[i] = true;
        this.bloom[j] = true;

    }


    /** convert permet d'obtenir une valeur en int pour n'importe quel string donné. */
    private int convert (String value){

        int sum=0;

        for (int i = 0; i<value.length();i++){
            sum += (int)value.charAt(i);
        }
        return sum;

    }


    /** fill va permettre d'insérer plus rapidement un tableau de valeur directement dans le filtre. */
    public void fill (String [] array){

        for (int k = 0 ; k < array.length ; k++){

            this.insertValue(array[k]);

        }
    }


    /** contains hache la valeur qu'on lui a passé en paramêtre. Ensuite, elle veriffie que les indices
     *  correspondant aux hashCodes, ont des valeurs à true dans le filtre. Si les deux indices sont a true alors il
     *  est possible que la valeur soit contenu dans le filtre de bloom sinon il est certain qu'elle n'est pas présente.*/
    public boolean contains (String value){

        int i = hashOne(value);
        int j = hashTwo(value);

        return (bloom[i] && bloom[j]);
    }


    /** isInto est une simple fonction d'affichage non nécessaire, elle a simplement été implémenté pour envoyer un
     * retour utilisateur*/
    public void isInto (String value){

        System.out.println("\nis \""+value+"\" into the bloom filter ?");

        if (this.contains(value)){
            System.out.println("Probably ¯\\_(ツ)_/¯ ( Probalbility of a false positive : " + this.probability() + "% )");
        }
        else{
            System.out.println("Absolutely not!");
        }

    }


    /** probability detemine la probabilité qu'un resultat vrai a la fonction contains, soit un faux positif*/
    public int probability() {

        double formula = 0,prob;
        double m = this.bloom.length;
        double n = this.bloomSize, k=2;

        formula = Math.pow(1-Math.exp((-k*n)/m),k);// Formule : (1-e( (-k*n)/n )²
        prob = formula *100;

        return (int)prob;
    }


    /** display permet d'afficher un filtre, simple fonction d'affichage pour l'utilisateur*/
    public void display() {

       System.out.print("| ");

       for (int i = 0 ; i < bloom.length; i++){
            if (bloom[i]==true){
                System.out.print(" 1 |");
            }
            else{
                System.out.print(" 0 |");
            }

       }

       System.out.println("");
   }


    /** bloomUnion prend en parametre 2 filtre et retourne un filtre de bloom qui correspond a l'union de ces deux
     *  filtre. */

    public Bloom bloomUnion (Bloom bloomBinded1,Bloom bloomBinded2){

       Bloom maxiBloom;
       Bloom miniBloom;

       if (bloomBinded1.bloomSize >= bloomBinded2.bloomSize ){

           maxiBloom = bloomBinded1;
           miniBloom = bloomBinded2;

       }
       else{

           maxiBloom = bloomBinded2;
           miniBloom = bloomBinded1;
       }

       Bloom bloomRes = new Bloom(maxiBloom.bloomSize);

       for (int i = 0; i < miniBloom.bloom.length ; i++) {

           if (bloomBinded1.bloom[i] || bloomBinded2.bloom[i]) {

               bloomRes.bloom[i] = true;
           }
       }
       for(int i = miniBloom.bloom.length ; i <maxiBloom.bloom.length;i++ ) {

           bloomRes.bloom[i]=maxiBloom.bloom[i];
       }

        return bloomRes;
   }


    /** bloomIntersection prend en parametre 2 filtre et retourne un filtre de bloom qi correspond a l'intersection
     *  des deux filtre.*/

    public Bloom bloomIntersection (Bloom bloomBinded1,Bloom bloomBinded2 ){


        Bloom maxiBloom;
        Bloom miniBloom;

        int compteur =0;

        if (bloomBinded1.bloomSize >= bloomBinded2.bloomSize ){

            maxiBloom = new Bloom (bloomBinded1.bloomSize);
            miniBloom = new Bloom (bloomBinded2.bloomSize);

        }
        else{

            maxiBloom = new Bloom (bloomBinded2.bloomSize);
            miniBloom = new Bloom (bloomBinded1.bloomSize);
        }

        Bloom bloomRes = new Bloom(maxiBloom.bloomSize);


        for (int i = 0 ; i < miniBloom.bloom.length ; i++){

            if (bloomBinded1.bloom[i] == true && bloomBinded2.bloom[i] == true ){

                bloomRes.bloom[i] = true;

            }
            else  {

                bloomRes.bloom[i] = false;

                compteur++;
            }

        }


        return bloomRes;

    }

}
