package jp.co.plusize.ota_tomomi.calculate_sales;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class readbranch {
	public static void main (String[] args){

		//支店コード、商品コードをキーとして支店名・商品名を参照するマップリストの定義
		HashMap<String,String> map = new HashMap<String,String>();

		//支店コード、商品コードをキーとして売上金額を参照するマップリストの定義
		HashMap<String,Long> revenuemap = new HashMap<String,Long>();


		//ArrayList branchNumberList = new ArrayList();
		//ArrayList commodityNumberList = new ArrayList();
		ArrayList revenueList = new ArrayList();


		//支店定義ファイルの読み込み
		try{
			File fileb = new File(args[0] + "\\branch.list");
			FileReader frb = new FileReader(fileb);
			BufferedReader brb = new BufferedReader(frb);
			String sb;



				while((sb = brb.readLine()) != null){
					String[] cols = sb.split(",");     //カンマによる文字列の分割

					try{
						//支店定義ファイルのフォーマットが正しいかどうか判定（桁数、カンマの有無、半角数値）
						if(cols[0].length() != 3 || cols.length > 2 ||  isNumber(cols[0])){
							System.out.println("支店定義ファイルのフォーマットが不正です");
							return;

						}

						//支店コードをキーとして支店名をマップリストに追加
						map.put(cols[0],cols[1]);//支店名・商品名のマップ
						revenuemap.put(cols[0],new Long(0));//売上金額のマップ


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
				    	//商品定義ファイルのフォーマットが正しいかどうか判定（桁数、カンマの有無、半角英数値）
						if(cols[0].length() != 8|| cols.length > 2 || isAlphabet(cols[0])){
							System.out.println("商品定義ファイルのフォーマットが不正です");
							return;
						}

				    	//商品コードをキーとして商品名をマップリストに追加
						map.put(cols[0],cols[1]);//支店名・商品名のマップ
						revenuemap.put(cols[0],new Long(0));//売上金額のマップ



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


		//売上ファイルの検索

		File dir = new File(args[0]);
		String[] files = dir.list(new MyFilter());


		for(int i = 0; i < files.length; i++){
			//   System.out.println(files[i]);

			//売上ファイル読み込み
			try{
				File rcdfile = new File(args[0] + "\\" + files[i]);
				FileReader rcdfr = new FileReader(rcdfile);
				BufferedReader rcdbr = new BufferedReader(rcdfr);
				String rcds;

				//売上ファイルデータ集計
				while((rcds = rcdbr.readLine()) != null){
					revenueList.add(rcds);


					}
				for(int j = 0; j < revenueList.size(); j++){
					System.out.println(revenueList.get(j));
				}

				rcdbr.close();
			}
			catch(IOException e){
				System.out.println("予期せぬエラーが発生しました");

			}
			//売上集計
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
	public static class MyFilter implements FilenameFilter{
		public boolean accept(File dir,String name){
			int index = name.lastIndexOf(".");

			//"."以下の文字列を取り出して小文字にする
			String ext = name.substring(index + 1).toLowerCase();

			//"."以前の文字列を取り出す
			String fileName = name.substring(0,index);





			//拡張子がrcdかつファイル名が8桁（連番の条件を入れたい）
			if(ext.equals("rcd") == true && fileName.length() == 8){
				return true;
				}



			return false;
	}
}
}














