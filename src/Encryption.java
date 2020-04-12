public class Encryption {
	private int[] K1 = new int[8];
	private int[] K2 = new int[8];
	private int[] pt = new int[8];

	int[] encrypt(int[] bin_msg8, int[] LK, int[] RK) {
		pt = bin_msg8;
		K1 = LK;
		K2 = RK;
		Print.msg("Plain/cipher text array : ");
		Print.array(this.pt, 8);
		Print.msg("\n");
		Print.msg("\n---------------------------------------\n");
		InitialPermutation();
		Print.msg("\n---------------------------------------\n");
//Separate left half & right half from 8-bit pt
		int[] LH = new int[4];
		int[] RH = new int[4];
		LH[0] = pt[0];
		LH[1] = pt[1];
		LH[2] = pt[2];

		LH[3] = pt[3];
		RH[0] = pt[4];
		RH[1] = pt[5];
		RH[2] = pt[6];
		RH[3] = pt[7];
		Print.msg("First Round LH : ");
		Print.array(LH, 4);
		Print.msg("\n");
		Print.msg("First Round RH: ");
		Print.array(RH, 4);
		Print.msg("\n");
//first round with sub-key K1
		int[] r1 = new int[8];
		r1 = functionFk(LH, RH, K1);
		Print.msg("After First Round : ");
		Print.array(r1, 8);
		Print.msg("\n");
		Print.msg("\n---------------------------------------\n");
//Switch the left half & right half of the output
		int[] temp = new int[8];
		temp = switchSW(r1);
		Print.msg("After Switch Function : ");
		Print.array(temp, 8);
		Print.msg("\n");
		Print.msg("\n---------------------------------------\n");
// again separate left half & right half for second round
		LH[0] = temp[0];
		LH[1] = temp[1];
		LH[2] = temp[2];
		LH[3] = temp[3];
		RH[0] = temp[4];
		RH[1] = temp[5];
		RH[2] = temp[6];
		RH[3] = temp[7];
		Print.msg("Second Round LH : ");
		Print.array(LH, 4);
		Print.msg("\n");
		Print.msg("Second Round RH: ");
		Print.array(RH, 4);
		Print.msg("\n");
//second round with sub-key K2
		int[] r2 = new int[8];
		r2 = functionFk(LH, RH, K2);
		pt = r2;
		Print.msg("After Second Round : ");
		Print.array(this.pt, 8);
		Print.msg("\n");
		Print.msg("\n---------------------------------------\n");
		InverseInitialPermutation();

		Print.msg("After Inverse IP (Result) : ");
		Print.array(this.pt, 8);
		Print.msg("\n");
//Encryption done... return 8-bit output .
		return pt;
	}

	/** perform Initial Permutation in following manner [2 6 3 1 4 8 5 7] **/
	void InitialPermutation() {
		int[] temp = new int[8];
		temp[0] = pt[1];
		temp[1] = pt[5];
		temp[2] = pt[2];
		temp[3] = pt[0];
		temp[4] = pt[3];
		temp[5] = pt[7];
		temp[6] = pt[4];
		temp[7] = pt[6];
		pt = temp;
		Print.msg("Initial Permutaion(IP) : ");
		Print.array(this.pt, 8);
		Print.msg("\n");
	}

	void InverseInitialPermutation() {
		int[] temp = new int[8];
		temp[0] = pt[3];
		temp[1] = pt[0];
		temp[2] = pt[2];
		temp[3] = pt[4];
		temp[4] = pt[6];
		temp[5] = pt[1];
		temp[6] = pt[7];
		temp[7] = pt[5];
		pt = temp;
	}

	/** mappingF . arguments 4-bit right-half of plaintext & 8-bit subkey **/
	int[] mappingF(int[] R, int[] SK) {
		int[] temp = new int[8];
// EXPANSION/PERMUTATION [4 1 2 3 2 3 4 1]
		temp[0] = R[3];
		temp[1] = R[0];
		temp[2] = R[1];
		temp[3] = R[2];
		temp[4] = R[1];
		temp[5] = R[2];
		temp[6] = R[3];
		temp[7] = R[0];
		Print.msg("EXPANSION/PERMUTATION on RH : ");
		Print.array(temp, 8);
		Print.msg("\n");

// Bit by bit XOR with sub-key
		temp[0] = temp[0] ^ SK[0];
		temp[1] = temp[1] ^ SK[1];
		temp[2] = temp[2] ^ SK[2];
		temp[3] = temp[3] ^ SK[3];
		temp[4] = temp[4] ^ SK[4];
		temp[5] = temp[5] ^ SK[5];
		temp[6] = temp[6] ^ SK[6];
		temp[7] = temp[7] ^ SK[7];
		Print.msg("XOR With Key : ");
		Print.array(temp, 8);
		Print.msg("\n");
// S-Boxes
		final int[][] S0 = { { 1, 0, 3, 2 }, { 3, 2, 1, 0 }, { 0, 2, 1, 3 }, { 3, 1, 3, 2 } };
		final int[][] S1 = { { 0, 1, 2, 3 }, { 2, 0, 1, 3 }, { 3, 0, 1, 0 }, { 2, 1, 0, 3 } };
		String d11_14 = new String();
		d11_14 = d11_14 + temp[0]; // first bit of first half
		d11_14 = d11_14 + temp[3]; // fourth bit of first half
		int row1 = BinToDec(d11_14); // for input in s-box S0
		String d12_13 = new String();
		d12_13 = d12_13 + temp[1]; // second bit of first half
		d12_13 = d12_13 + temp[2]; // third bit of first half
		int col1 = BinToDec(d12_13); // for input in s-box S0
		int o1 = S0[row1][col1];
		int[] out1 = DecToBinArr(o1);
		Print.msg("S-Box S0: ");
		Print.array(out1, 2);
		Print.msg("\n");
		String d21_24 = new String();
		d21_24 = d21_24 + temp[4]; // first bit of second half
		d21_24 = d21_24 + temp[7]; // fourth bit of second half
		int row2 = BinToDec(d21_24);
		String d22_23 = new String();
		d22_23 = d22_23 + temp[5]; // second bit of second half
		d22_23 = d22_23 + temp[6]; // third bit of second half
		int col2 = BinToDec(d22_23);
		int o2 = S1[row2][col2];
		int[] out2 = DecToBinArr(o2);
		Print.msg("S-Box S1: ");
		Print.array(out2, 2);
		Print.msg("\n");
//4 output bits from 2 s-boxes
		int[] out = new int[4];
		out[0] = out1[0];
		out[1] = out1[1];
		out[2] = out2[0];
		out[3] = out2[1];
//permutation P4 [2 4 3 1]
		int[] O_Per = new int[4];
		O_Per[0] = out[1];
		O_Per[1] = out[3];

		O_Per[2] = out[2];
		O_Per[3] = out[0];
		Print.msg("Output of mappingF : ");
		Print.array(O_Per, 4);
		Print.msg("\n");
		return O_Per;
	}

	/** fK(L, R, SK) = (L (XOR) mappingF(R, SK), R) .. returns 8-bit output **/
	int[] functionFk(int[] L, int[] R, int[] SK) {
		int[] temp = new int[4];
		int[] out = new int[8];
		temp = mappingF(R, SK);
//XOR left half with output of mappingF
		out[0] = L[0] ^ temp[0];
		out[1] = L[1] ^ temp[1];
		out[2] = L[2] ^ temp[2];
		out[3] = L[3] ^ temp[3];
		out[4] = R[0];
		out[5] = R[1];
		out[6] = R[2];
		out[7] = R[3];
		return out;
	}

	/** switch function (SW) interchanges the left and right 4 bits **/
	int[] switchSW(int[] in) {
		int[] temp = new int[8];
		temp[0] = in[4];
		temp[1] = in[5];
		temp[2] = in[6];
		temp[3] = in[7];
		temp[4] = in[0];
		temp[5] = in[1];
		temp[6] = in[2];
		temp[7] = in[3];
		return temp;
	}

	private int BinToDec(String bin) {
		int dec;
		dec = Integer.parseInt(bin, 2);
		return dec;
	}

	private int[] DecToBinArr(int o2) {

		if (o2 == 0) {
			int[] zero = new int[2];
			zero[0] = 0;
			zero[1] = 0;
			return zero;
		} else {
			int y;
			int[] temp = new int[2];
			int k = temp.length - 1;

			while (o2 > 0)

			{
				y = o2 % 2;
				o2 = o2 / 2;
				temp[k--] = y;
			}

			return temp;

		}
	}
}