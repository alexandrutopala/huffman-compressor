import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Arrays;
import compress.implementation.*;

public class Main {

//    private static String alegeTest(){
//        String a = "";
//        Scanner sca = new Scanner(System.in);
//        System.out.println("Introduceti testul pe care doriti sa il cititi: ");
//        System.out.println("Exemplu de introducere: ");
//        System.out.println("test1.txt");
//        a = sca.nextLine();
//        return a;
//    }

    public static void main(String[] args) {

        String comanda = args[0];
        System.out.println(comanda);

        String source = args[1]; // Arrays.toString(new String[]{args[1]});  // path_to_source -- file   -- citeste
        System.out.println(source);
        String dest = args[2];   // Arrays.toString(new String[]{args[2]});  // path_to_dest  -- fisier  -- scrie
        System.out.println(dest);

        // alegerea testului
//        String Test = alegeTest();
//        source = source + "/" + Test;    // file separator - ca sa mearga si pe Linux -- e si la args ca la Windows
//        System.out.println(source);

        // citire din fisier
        String stringCitit = "";
        try {
            File file = new File(source);
            BufferedReader br = new BufferedReader(new FileReader(file) );
            while (true) {
                String str = br.readLine();
                if (str == null ) break;
                else {
                    stringCitit = stringCitit + str;
                }
            }
        }catch (IOException e) {
            System.out.println("Eroare la citire");
        }
        // citeste corect din fisier
        System.out.println("stringCitit este : ");
        System.out.println(stringCitit);

        // apelare
        File fisier = new File(dest);
        FileWriter f = null;

        byte[] compressed;
        String decompressed = "";

// daca citesc : Ana are mere.
// si fac decompress din compressed obtin textul initial   --- super :)
//
//        compressed = Compressors.getFor(String.class).compress(stringCitit) ;
//        System.out.println("compressed este : ");
//        System.out.println(compressed);
//
//        decompressed = Compressors.getFor(String.class).decompress(compressed);
//        System.out.println("decompressed este : ");
//        System.out.println(decompressed);
//
//        if (stringCitit.equals(decompressed) ) { // e ok
//            System.out.println("A mers!");
//        }
//        else { // nu e ok
//            System.out.println("A aparut o problema!");
//        }
//

// --------------------------------------------------------    :(
// am pus intr-un fisier.txt codul : [B@439f5b3d
// a crapat la decompress - >  de ce ?
/*
output :
decompressFile
./TESTE/byte1.txt
./REZULTATE/REZ.txt
stringCitit este :
[B@439f5b3d
Ai ales decompressFile.
Exception in thread "main" java.nio.BufferUnderflowException
	at java.base/java.nio.HeapByteBuffer.get(HeapByteBuffer.java:179)
	at java.base/java.nio.ByteBuffer.get(ByteBuffer.java:804)
	at compress.implementation.HuffmanTextCompressor.decodeDictionary(HuffmanTextCompressor.java:100)
	at compress.implementation.HuffmanTextCompressor.decompress(HuffmanTextCompressor.java:85)
	at compress.implementation.HuffmanTextCompressor.decompress(HuffmanTextCompressor.java:12)
	at Main.main(Main.java:102)

Process finished with exit code 1

 */


        // executia de compresie sau decompresie
        if(comanda.equals("compressFile") ) {
            System.out.println("Ai ales compressFile. ");
            compressed = Compressors.getFor(String.class).compress(stringCitit);
            System.out.println("compressed este : ");
            System.out.println(compressed);

            // scriere
            try {
                f = new FileWriter(fisier);
                f.write(String.valueOf(compressed) );
                f.close();
            } catch (IOException e) {
                System.out.println("Eroare la scriere text decomprimat");
            }
        }else {
            System.out.println("Ai ales decompressFile. ");
            byte[] bytes = stringCitit.getBytes(StandardCharsets.UTF_8);
            decompressed = Compressors.getFor(String.class).decompress(bytes);
            System.out.println("decompressed este : ");
            System.out.println(decompressed);

            // scriere
            try {
                f = new FileWriter(fisier);
                f.write(String.valueOf(decompressed) );
                f.close();
            } catch (IOException e) {
                System.out.println("Eroare la scriere text decomprimat");
            }
        }


    }
}

