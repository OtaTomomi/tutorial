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
public class SalesData{
	public static void main (String[] args){
		//コマンドライン引数が渡されていなかったとき、2つ以上あるときの処理
		if(args.length != 1){
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		//支店コードをキーとして支店名を参照するマップリストの定義
		HashMap<String,String> branchMap = new HashMap<String,String>();
		//商品コードをキーとして商品名を参照するマップリストの定義
		HashMap<String,String> commodityMap = new HashMap<String,String>();
		//支店コードをキーとして支店別の売上金額を参照するマップリストの定義
		HashMap<String,Long> branchSaleMap = new HashMap<String,Long>();
		//商品コードをキーとして商品別の売上金額を参照するマップリストの定義
		HashMap<String,Long> commoditySaleMap = new HashMap<String,Long>();
		//リストアップした売上ファイルの名前を数値化したものを格納するためのデータ
		ArrayList<Integer> fileList = new ArrayList<Integer>();
		BufferedReader br = null;
		//定義ファイルの存在確認
		File branchFile = new File(args[0] + File.separator + "branch.lst");
		File commodityFile = new File(args[0] + File.separator + "commodity.lst");
		if(!branchFile.exists()){
			System.out.println("支店定義ファイルが存在しません");
			return;
		}
		if(!commodityFile.exists()){
			System.out.println("商品定義ファイルが存在しません");
			return;
		}
		//支店定義ファイルの読み込み
		String branchRead = readData(args[0] + File.separator + "branch.lst", branchSaleMap, branchMap, "^\\d{3}$");
		String commodityRead = readData(args[0] + File.separator + "commodity.lst", commoditySaleMap, commodityMap, "^\\w{8}$");
		if(branchRead != "" ){
			if(branchRead.equals("予期せぬエラーが発生しました")){
				System.out.println(branchRead);
				return;
			}
			System.out.println("支店" + branchRead);
			return;
		}
		//商品定義ファイルの読み込み
		if(commodityRead != ""){
			if(commodityRead.equals("予期せぬエラーが発生しました")){
				System.out.println(commodityRead);
				return;
			}
			System.out.println("商品" + commodityRead);
			return;
		}
		//売上ファイルの検索
		File dir = new File(args[0]);
		String[] files = dir.list(new fileListFilter());
		//連番かどうか
		for(int i = 0; i < files.length; i++){
			File fileOrDirectry = new File(args[0] + File.separator + files[i]);
			if(fileOrDirectry.isFile()){
				fileList.add(Integer.parseInt(files[i].substring(0, 8)));
			}
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
			//読み込んだ売上ファイルの情報を一時的に格納するためのリストの定義
			ArrayList<String> saleList = new ArrayList<String>();
			//売上ファイル読み込み
			try{
				File file = new File(args[0] + File.separator + files[i]);
				FileReader fr = new FileReader(file);
				br = new BufferedReader(fr);
				String data;
				while((data = br.readLine()) != null){
					saleList.add(data);
				}
				if(saleList.size() != 3){
					System.out.println(files[i] +"のフォーマットが不正です");
					return;
				}
				if(!branchSaleMap.containsKey(saleList.get(0))){
					System.out.println(files[i] +"の支店コードが不正です");
					return;
				}
				if(!commoditySaleMap.containsKey(saleList.get(1))){
					System.out.println(files[i] +"の商品コードが不正です");
					return;
				}
				//支店別集計
				if(!makeSaleList(saleList, branchSaleMap, 0)){
					return;
				}
				//商品別集計
				if(!makeSaleList(saleList, commoditySaleMap, 1)){
					return;
				}
			} catch(IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return;
			//long型に格納できない桁数だったときのエラー
			} catch(NumberFormatException e) {
				System.out.println("合計金額が10桁を超えました");
				return;
			} finally {
				try {
					if(br != null){
						br.close();
					}
				} catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
					return;
				}
			}
		}
		//支店別集計ファイルの作成
		if(!makeSaleData(args[0] + File.separator + "branch.out", branchSaleMap, branchMap)){
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		//商品別集計ファイルの作成
		if(!makeSaleData(args[0] + File.separator + "commodity.out", commoditySaleMap, commodityMap)){
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
	}
	//ファイルを検索するためのクラス
	public static class fileListFilter implements FilenameFilter{
		public boolean accept(File dir, String name){
			String[] cols = name.split("\\.");
			if(cols.length != 2){
				return false;
			}
			if(cols[1].equals("rcd") && cols[0].length() == 8){
				return true;
				}
			return false;
		}
	}
	//売上集計ファイル作成のためのメソッド
	public static boolean makeSaleData(String fileName, HashMap<String,Long> saleMap, HashMap<String,String> codeMap){
		BufferedWriter bw = null;
		try{
			File file = new File(fileName);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			//降順のソートの作成
			List<Map.Entry<String,Long>> entries = new ArrayList<Map.Entry<String,Long>>(saleMap.entrySet());
			Collections.sort(entries, new Comparator<Map.Entry<String,Long>>() {
			public int compare(
						Entry<String,Long> entry1, Entry<String,Long> entry2) {
					return (entry2.getValue()).compareTo(entry1.getValue());
					}
				});
			//出力
			for(Map.Entry<String,Long> outputEntry :entries){
				bw.write(outputEntry.getKey() + "," + codeMap.get(outputEntry.getKey()) + "," + outputEntry.getValue());
				bw.newLine();
			}
			return true;
		} catch(IOException e) {
			return false;
		} finally {
			try {
				if(bw != null){
					bw.close();
				}
			} catch (IOException e) {
				return false;
			}
		}
	}
	//定義ファイル読み込みのためのメソッド
	public static String readData(String fileName, HashMap<String,Long> saleMap, HashMap<String,String> codeMap, String codeTerm){
		BufferedReader br = null;
		try{
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String data;
			while((data = br.readLine()) != null){
				String[] cols = data.split(",");     //カンマによる文字列の分割
				if(cols.length != 2 || !(cols[0].matches(codeTerm))){
					return "定義ファイルのフォーマットが不正です";
				}
				//支店・商品コードをキーとして支店・商品名をマップリストに追加
				codeMap.put(cols[0], cols[1]);//支店・商品名のマップ
				//支店・商品コードをキーとして支店・商品ごとの売上金額を格納するマップリストを作成
				saleMap.put(cols[0], new Long(0));//支店・商品別売上金額のマップ
			}
			return "";
		} catch(IOException e) {
			return "予期せぬエラーが発生しました";
		} finally {
			try {
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				return "予期せぬエラーが発生しました";
			}
		}
	}
	//売上額を集計するメソッド
	public static boolean makeSaleList(ArrayList<String> list, HashMap<String,Long> saleMap, int codeLine){
		saleMap.put(list.get(codeLine),saleMap.get(list.get(codeLine)) + Long.parseLong(list.get(2)));
		//10桁を超えたらエラーを返す
		if(String.valueOf(saleMap.get(list.get(codeLine))).length() > 10){
			System.out.println("合計金額が10桁を超えました");
			return false;
		}
		return true;
	}
}
