package jp.co.plusize.ota_tomomi.calculate_sales;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;


public class readbranch {
	public static void main (String[] args){

		HashMap<String,String> map = new HashMap<String,String>();

		//支店定義ファイルの読み込み
		try{
			File fileb = new File(args[0] + "\\branch.list");
			FileReader frb = new FileReader(fileb);
			BufferedReader brb = new BufferedReader(frb);
			String sb;



				while((sb = brb.readLine()) != null){
					String[] cols = sb.split(",");     //カンマによる文字列の分割

					try{
						//支店コードをキーとして支店名をマップリストに追加
						map.put(cols[0],cols[1]);
						System.out.println(map.get(cols[0]));


						//支店定義ファイルのフォーマットが正しいかどうか判定（桁数、カンマの有無、半角数値）
						if(cols[0].length() != 3 || cols.length > 2 ||  isNumber(cols[0])){
							System.out.println("支店定義ファイルのフォーマットが不正です");
							return;

						}


					}
					//支店名に改行が含まれたときの例外処理
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("支店定義ファイルのフォーマットが不正です");
						return;

						}

				}
				brb.close();


			}
		catch(IOException e){
			System.out.println("支店定義ファイルが存在しません");
			return;
		}

		//商品定義ファイルの読み込み
		try{
			File filec = new File(args[0] + "\\commodity.list");
			FileReader frc = new FileReader(filec);
			BufferedReader brc = new BufferedReader(frc);
			String sc;

			while((sc = brc.readLine()) != null){
				String[] cols = sc.split(",");     //カンマによる文字列の分割

				    try{
				    	//商品コードをキーとして商品名をマップリストに追加
						map.put(cols[0],cols[1]);
						System.out.println(map.get(cols[0]));

						//商品定義ファイルのフォーマットが正しいかどうか判定（桁数、カンマの有無、半角英数値）
						if(cols[0].length() != 8|| cols.length > 2 || isAlphabet(cols[0])){
							System.out.println("商品定義ファイルのフォーマットが不正です");
							return;
							}

						}
				    //商品名に改行が含まれたときの例外処理
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("商品定義ファイルのフォーマットが不正です");
						return;

						}
				    }


			brc.close();
		}
		catch(IOException e){
			System.out.println("商品定義ファイルが存在しません");
			return;
		}


		//売上ファイルの検索(未完！！)

		File dir = new File(args[0]);
		String[] files = dir.list(new MyFilter());
		for(int i = 0; i < files.length; i++){
			System.out.println(files[i]);
		}
}

//半角数値以外のときtrue
	public static boolean isNumber(String s) {
	    	return Pattern.compile("\\D").matcher(s).find();
}
//半角英数値以外のときtrue
	public static boolean isAlphabet(String s) {
		return Pattern.compile("\\W").matcher(s).find();
	}
	//ファイルを検索するためのクラス(未完！！！)
	class MyFilter implements FilenameFilter{


		}
}







}







