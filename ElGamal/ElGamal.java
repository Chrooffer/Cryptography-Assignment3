import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;


public class ElGamal {

  public static String decodeMessage(BigInteger m) {
    return new String(m.toByteArray());
  }

  public static void main(String[] arg) {
    String filename = "input.txt";
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      BigInteger p = new BigInteger(br.readLine().split("=")[1]);
      BigInteger g = new BigInteger(br.readLine().split("=")[1]);
      BigInteger y = new BigInteger(br.readLine().split("=")[1]);
      String line = br.readLine().split("=")[1];
      String date = line.split(" ")[0];
      String time = line.split(" ")[1];
      int year  = Integer.parseInt(date.split("-")[0]);
      int month = Integer.parseInt(date.split("-")[1]);
      int day   = Integer.parseInt(date.split("-")[2]);
      int hour   = Integer.parseInt(time.split(":")[0]);
      int minute = Integer.parseInt(time.split(":")[1]);
      int second = Integer.parseInt(time.split(":")[2]);
      BigInteger c1 = new BigInteger(br.readLine().split("=")[1]);
      BigInteger c2 = new BigInteger(br.readLine().split("=")[1]);
      br.close();
      BigInteger m = recoverSecret(p, g, y, year, month, day, hour, minute,
          second, c1, c2);
      System.out.println("Recovered message: " + m);
      System.out.println("Decoded text: " + decodeMessage(m));
    } catch (Exception err) {
      System.err.println("Error handling file.");
      err.printStackTrace();
      System.exit(1);
    }
  }

  public static BigInteger recoverSecret(BigInteger p, BigInteger g,
      BigInteger y, int year, int month, int day, int hour, int minute,
      int second, BigInteger c1, BigInteger c2) {
    return c1;
  }

}


/*

Attacking ElGamal (10 points)
Thanks to its random component, two ElGamal encryptions
of the same message can look completely different.
However, this also makes the strength of the encryption
depend on the random number generation. In this assignment
you will attack ElGamal under a weak number generator.

We intercepted you an ElGamal encryption of a message
together with a partial time stamp at which it was encrypted.
You can find a pseudo-code describing how the sender is choosing
her randomness to encrypt the messages here.
Your task is decrypt the message exploiting the weakness of
this bad Pseudo Random Number Generator.
Please do not break ElGamal's Discrete Log problem, otherwise
it would be a threat against the security of the whole internet!

The text field below lists the group size (p), the generator
of the group (g), the public key of the receiver (y),
the time at which the message was encrypted
(and the PRNG was called), and finally the ElGamal
encryption of the message (c1, c2).


The following is the pseudo code fo the 'pseudo random number generator' used by the sender
of the ElGamal encrypted message to generate the random number in the ElGamal encryption.

integer createRandomNumber:
    return YEAR*(1010) + month*(108) + days*(106) + hours*(104) + minute*(102) + sec + millisecs;

A possible result of the function is, for example, 20160113108765.


p=173846371468707362418981635461056900742995737101751198893797259020642003570117604072212124021709733811235147197773926904181283985845917166875128967179806447321585076378587223254771929294102226061818457877299295115011579797293271192977409189209286061119705010756468616429423980863442972331149798604191555775437
g=106720663699807598090466338020749772293164632188415631754552865868439983021950081639371780488023673046534084067583697411600738897612034506871775379700729647918363286102067207131482924263139163314179323155514891159210538827115220059235910451111808064062159141269329373890323061133522022021554594819312464874400
y=55979409526142748051025179954766247335025969922544046783790971996941470360624027208801310133103590275013556779539429660887070536217776606518831871210014724162829158673635270024152709455314913474502432445689120137013398013030860509565360041751353582374788283409904595716009498894339708554476395981866696081322
time=2015-12-25 12:30:59
c1=155266370160615024729502827995633958488924883426434081844274548531404563705023659040043874150166124388443718904893318970805441564344801840646677276062097532501172593060550892141529636881694363706844469490756255908124747118174078829027920097432745937162720905462372907664065582506409647921250376138895368542863
c2=2486680156329994850956849485750060820702507971471548129521648486386920889983384074399080506410520070517506345967486101278776746217746071081267247734295990936037915811388369075753571576598766241164130019547793513715052450147216152617723516602345838752324346890748442393278587669963044917568966813415921619430
*/
