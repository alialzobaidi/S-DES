import java.util.Scanner;

public class SDES {

	private static Scanner sc;

	public static void main(String[] args) {

		sc = new Scanner(System.in);

		KeyGeneration KG = new KeyGeneration();
		Encryption enc = new Encryption();
		String pt;
		String key;
//Ex Input : 1010000010
		System.out.print("Enter 10-bit Key : ");
		key = sc.next();
		System.out.println(" \n ");
		Print.msg("\n Key Generation ...\n");
		Print.msg("\n---------------------------------------\n");
		KG.GenerateKeys(key);
		Print.msg("\n---------------------------------------\n");
//Ex Input : hello
		System.out.print("Enter Plaintext : ");
		pt = sc.next();
		int[] bin_msg = char_binary(pt);
		int[] ct = new int[pt.length() * 8];
		int c = 0, ctcount = 0;
		for (int i = 0; i < pt.length(); i++) {
			System.out.println("character " + (i + 1));
//take 8bits from the msg at a time and encrypt it(block cipher)
			int[] bin_msg8e = new int[8];
			int[] ct8e = new int[8];
			for (int j = 0; j < 8; j++) {
				bin_msg8e[j] = bin_msg[c];
				c++;
			}

			System.out.println(" \n ");
			ct8e = enc.encrypt(bin_msg8e, KG.k1, KG.k2);
			for (int j = 0; j < 8; j++)

			{
				ct[ctcount] = ct8e[j];
				ctcount++;
			}

			Print.msg("\n---------------------------------------\n");

		}

		System.out.println(" \n Decryption ");

		Print.msg("\n---------------------------------------\n");
		Print.msg("\n For decryption Two Sub-keys will be used in reverse order\n");

		Print.msg("\n---------------------------------------\n");
		int ptcount = 0;
		c = 0;
		for (int i = 0; i < pt.length(); i++)

		{

			System.out.println("character " + (i + 1));

//take 8bits from the ciphertext at a time and decrypt it(block cipher)
			int[] bin_msg8d = new int[8];
			int[] ct8d = new int[8];
			for (int j = 0; j < 8; j++) {
				bin_msg8d[j] = ct[c];
				c++;
			}

			System.out.println(" \n ");
			ct8d = enc.encrypt(bin_msg8d, KG.k2, KG.k1);
			for (int j = 0; j < 8; j++)

			{
				bin_msg[ptcount] = ct8d[j];
				ptcount++;
			}

			Print.msg("\n---------------------------------------\n");

		}

		String plain_char = binary_char(bin_msg);
		System.out.println("plan as char::" + plain_char);
	}

	public static int[] char_binary(String p) {
		int count = 0;
		int[] bin_msg = new int[p.length() * 8];
		int y;
		for (int i = 0; i < p.length(); i++) {
			int[] temp2 = new int[8];
			int k = temp2.length - 1;
			int q = (int) p.charAt(i);
			System.out.println(q);
			while (q > 0) {
				y = q % 2;
				q = q / 2;
				temp2[k--] = y;
			}
			for (int j = 0; j < temp2.length; j++) {
				bin_msg[count++] = temp2[j];
			}
		}
		return bin_msg;
	}

	public static String binary_char(int[] bin_msg) {
		String binary = new String();
		int sum = 0, t = 0;

		String plain = new String();
//
		for (int i = 0; i <= bin_msg.length; i++) {
			if (t == 8) {
				sum = Integer.parseInt(binary, 2);
				char ciph = (char) sum;
				plain = plain + ciph;
				sum = 0;
				t = 0;
				binary = "";
				if (i != bin_msg.length)
					--i;
			} else {
				binary = binary + bin_msg[i];
				t++;
			}
		}
		return plain;
	}
}
