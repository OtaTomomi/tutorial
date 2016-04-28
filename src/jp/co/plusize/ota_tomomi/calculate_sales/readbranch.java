package jp.co.plusize.ota_tomomi.calculate_sales;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;


public class readbranch {
	public static void main (String[] args){

		//支店コードをキーとして支店名を参照するマップリストの定義
		HashMap<String,String> branchmap = new HashMap<String,String>();
		//商品コードをキーとして商品名を参照するマップリストの定義
		HashMap<String,String> commoditymap = new HashMap<String,String>();

		//支店コードをキーとして支店別の売上金額を参照するマップリストの定義
		HashMap<String,Long> branchrevenuemap = new HashMap<String,Long>();
		//商品コードをキーとして商品別の売上金額を参照するマップリストの定義
		HashMap<String,Long> commodityrevenuemap = new HashMap<String,Long>();

		ArrayList<String> revenueList = new ArrayList<String>();
		ArrayList<Integer> fileList = new ArrayList<Integer>();



		//支店定義ファイルの読み込み
		try{
			File fileb = new File(args[0], "branch.lst");
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
						branchmap.put(cols[0],cols[1]);//支店名のマップ
						branchrevenuemap.put(cols[0],new Long(0));//支店別売上金額のマップ


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
			File filec = new File(args[0] , "commodity.lst");
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
						commoditymap.put(cols[0],cols[1]);//商品名のマップ
						commodityrevenuemap.put(cols[0],new Long(0));//商品別売上金額のマップ



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


		//連番かどうか
		for(int i = 0; i < files.length; i++){
			//System.out.println(files[i]);

			int index = files[i].lastIndexOf(".");
			fileList.add(Integer.parseInt(files[i].substring(0,index)));
			//System.out.println(fileList.get(i));


		}
		int max = fileList.get(0);
		int min = fileList.get(0);

		for (int i=0; i<fileList.size(); i++) {
			if (max < fileList.get(i)) {	//現在の最大値よりも大きい値が出たら
				max = fileList.get(i);	//変数maxに値を入れ替える
			}
			if (min > fileList.get(i)) {	//現在の最小値よりも小さい値が出たら
				min = fileList.get(i);	//変数minに値を入れ替える
			}
		}
		if(max - min != fileList.size() - 1){
			System.out.println("売上ファイル名が連番になっていません");
			return;
		}




		for(int i = 0; i < files.length; i++){
			//   System.out.println(files[i]);

			//売上ファイル読み込み
			try{
				File rcdfile = new File(args[0] + File.separator + files[i]);
				FileReader rcdfr = new FileReader(rcdfile);
				BufferedReader rcdbr = new BufferedReader(rcdfr);
				String rcds;


				while((rcds = rcdbr.readLine()) != null){
					revenueList.add(rcds);



				}
				if(revenueList.size() > 3){
					System.out.println(files[i] +"のフォーマットが不正です");
					return;
				}

				if(branchrevenuemap.containsKey(revenueList.get(0)) == false){
					System.out.println(files[i] +"の支店コードが不正です");
					return;
				}
				if(commodityrevenuemap.containsKey(revenueList.get(1)) == false){
					System.out.println(files[i] +"の商品コードが不正です");
					return;
				}


				//売上ファイルデータ集計
				//支店別
				branchrevenuemap.put(revenueList.get(0),branchrevenuemap.get(revenueList.get(0)) + Long.parseLong(revenueList.get(2)));
				commodityrevenuemap.put(revenueList.get(1),commodityrevenuemap.get(revenueList.get(1)) + Long.parseLong(revenueList.get(2)));


				//10桁を超えたらエラーを返す
				if(String.valueOf(branchrevenuemap.get(revenueList.get(0))).length() > 10 ){
					System.out.println("合計金額が10桁を超えました");
					return;
				}




				revenueList.clear();
				rcdbr.close();
			}
			catch(IOException e){
				System.out.println("予期せぬエラーが発生しました");
				return;

			}
			catch(IndexOutOfBoundsException e){
				System.out.println("予期せぬエラーが発生しました");
				return;

			}
			catch(NullPointerException e){
				System.out.println("予期せぬエラーが発生しました");
				return;

			}



			//long型に格納できない桁数だったときのエラー
			catch(NumberFormatException e){
				System.out.println("合計金額が10桁を超えました");
				return;

			}




		}
		//支店別集計ファイルの作成
		try{
			File branchfile = new File(args[0],"branch.out");
			branchfile.createNewFile();

			FileWriter fwb = new FileWriter(branchfile);
			BufferedWriter bwb = new BufferedWriter(fwb);

			//降順のソートの作成
			List<Map.Entry<String,Long>> branchentries = new ArrayList<Map.Entry<String,Long>>(branchrevenuemap.entrySet());
			Collections.sort(branchentries, new Comparator<Map.Entry<String,Long>>() {

	            public int compare(
	                  Entry<String,Long> entry1, Entry<String,Long> entry2) {
	                return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
	            }
	        });





			//branch.outへの出力
			for(Map.Entry<String,Long> e :branchentries){
				bwb.write(e.getKey() + "," + branchmap.get(e.getKey()) + "," + e.getValue() + "\r\n");


			}

			bwb.close();
		}
		catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
			return;

		}

		//商品別集計ファイルの作成
		try{
			File commodityfile = new File(args[0],"commodity.out");
			commodityfile.createNewFile();

			FileWriter fwc = new FileWriter(commodityfile);
			BufferedWriter bwc = new BufferedWriter(fwc);




			//降順のソートの作成
			List<Map.Entry<String,Long>> commodityentries = new ArrayList<Map.Entry<String,Long>>(commodityrevenuemap.entrySet());
			Collections.sort(commodityentries, new Comparator<Map.Entry<String,Long>>() {

	            public int compare(
	                  Entry<String,Long> entry1, Entry<String,Long> entry2) {
	                return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
	            }
	        });


			//commodity.outへの出力
			for(Map.Entry<String,Long> e : commodityentries){
				bwc.write(e.getKey() + "," + commoditymap.get(e.getKey()) + "," + e.getValue() + "\r\n");


			}

			bwc.close();
		}
		catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
			return;

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

			//拡張子がrcdかつファイル名が8桁
			if(ext.equals("rcd") == true && fileName.length() == 8){
				return true;
				}

			return false;
	}
}
}














