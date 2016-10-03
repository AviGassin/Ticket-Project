import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ElasticSearch {

	public static void main(String[] args) {
		int size=18;
		String[] arrayName={"Avi","Aviram","Avinoam","Avihai", "Avia Ben Tov","Avrum Hasid","Beni", "Baruh","Beri Saharof"
		,"Sharon", "Ramzi Bar El", "Zina", "Zigmond", "Dani","Danino","Daniel","Dror", "Avi"};
		Set<String> arrayNameSet=new TreeSet<String>();
		for(String str:arrayName){
			System.out.println(str);
			arrayNameSet.add(str);
		}
		System.out.println("Sorted array, extracted from HashSet");
		for(String str:arrayNameSet){
			System.out.println(str);
			
		}
		char letter='A';
		ElasticNode elasticNode=new ElasticNode(letter);
		String name1="Avi";
		insertIntoRepo(name1,name1,elasticNode);
		String name11="Avi";
		insertIntoRepo(name11,name11,elasticNode);
		String name2="Avinoam";
		insertIntoRepo(name2,name2,elasticNode);
		String name3="Avihai";
		insertIntoRepo(name3,name3,elasticNode);
		String name4="Avia";
		insertIntoRepo(name4,name4,elasticNode);
		String name5="Avshalom";
		insertIntoRepo(name5,name5,elasticNode);
		String name6="Avsi";
		insertIntoRepo(name6,name6,elasticNode);
		String name7="Aviahu";
		insertIntoRepo(name7,name7,elasticNode);
		
//		for(String arrayEntry:arrayName){
//			insertIntoRepo(arrayEntry,arrayEntry,elasticNode);
//		}
		
		System.out.println(getSuggestions("Avs","Avs",elasticNode));
		}
	
//	working assumption is- all inserted names are complete and not truncated.
	public static void insertIntoRepo(String truncName,String name, ElasticNode root){
		
//		stop condition, once reaching an endNode(leaf)
//		&&!truncName.equals("")
		if(truncName.length()==1&&!truncName.equals(null)){
			char letter=truncName.charAt(0);
//			if such entry exists, we will add the 'name' to the existing relatedWordList
			Map<Character,Set<String>> repoMapLocal=root.getRepoMap();
			if(repoMapLocal.containsKey(truncName)){
				repoMapLocal.get(letter).add(name);
				root.setRepoMap(repoMapLocal);
				return;
			}
//			if the entry does not exist, we will add an entry by creating relatedWordList with the relevant letter and insert the 'name'
//			into the newly created HashSet
			else{
				Set<String> relatedWordList=new HashSet<String>();
				relatedWordList.add(name);
				
				repoMapLocal=root.getRepoMap();
				repoMapLocal.put(letter,relatedWordList);
				root.setRepoMap(repoMapLocal);
				return;
			}
				
				
		}
/////////////			
		
//		truncName is greater than 2 letters
//		Avi==> vi==>i
//		&&!truncName.equals("")
		if(truncName.length()>=2&&!truncName.equals(null)){
			
			char letter=truncName.charAt(0);
//			creating the new truncName for the next iteration
			StringBuilder sb = new StringBuilder(truncName);
			sb.deleteCharAt(0);
			truncName=sb.toString();
			
//			1) first step: listing (repoMap maintenance) the name in our relatedWordList
			Map<Character,Set<String>> repoMapLocal=root.getRepoMap();
			if(repoMapLocal.containsKey(letter)){
				repoMapLocal.get(letter).add(name);
				root.setRepoMap(repoMapLocal);
			}else{
				Set<String> relatedWordList=new HashSet<String>();
				relatedWordList.add(name);
				repoMapLocal=root.getRepoMap();
				repoMapLocal.put(letter,relatedWordList);
				root.setRepoMap(repoMapLocal);
			}
			Map<Character,ElasticNode> mapLocal=root.getMap();
			char nextLetter=letter;  //it's one step ahead trimmed truncName truncName.charAt(0)
//			2) second step: checking whether there's a subsequent ElasticNode 	
				if(mapLocal.containsKey(nextLetter)){
//					routing to next ElasticNode with trimmed truncName and the original name;
					
					insertIntoRepo(truncName,name,mapLocal.get(nextLetter));
					return;
				} 
//				if the entry does not exist, i.e. there's no subsequent ElasticNode- we will create one
				else{
					ElasticNode elasticNode=new ElasticNode(nextLetter); // giving a value to the next ElasticNode
					mapLocal.put(nextLetter, elasticNode);
					root.setMap(mapLocal);
					insertIntoRepo(truncName,name,elasticNode);
					return;
				}
				
				
			}
			
			
			
		}
	
	
	public static Set<String> getSuggestions(String truncName,String initName,ElasticNode root){
		
//		stop condition
//		&&!truncName.equals(" ")
		if(truncName.length()==1&&!truncName.equals(null)){
//			look up whether it has relatedWordList
			char letter=truncName.charAt(0);
			Map<Character,Set<String>> repoMapLocal=root.getRepoMap();
			if(repoMapLocal.containsKey(letter)){
				return repoMapLocal.get(letter);
			}else {
				return null;  // i.e. no suggestions available
			}
			
		}
		
		
//		1) Case one: truncName.charAt(0)=" " - space between words
		char letter=truncName.charAt(0);
		if(truncName.length()>1&&!truncName.equals(null)&&Character.toString(letter).matches(" ")){
//			we will create or route to ElasticNode that holds char=" " - we will also have an entry in map and repoMap
//			for space (single one)
			System.out.println("you have reached an empty space ElasticNode");
		}
		
//		2) Case two: we are in mid truncName- will be trimmed it and send it forward
//		&&!Character.toString(letter).matches(" ")
		if(truncName.length()>1&&!truncName.equals(null)){
//			trim and send forward to the next ElasticNode , if exists 
//			if not exists, we will return null (nothing to suggest)
			StringBuilder stringBuilder=new StringBuilder(truncName);
			stringBuilder.deleteCharAt(0);
			truncName=stringBuilder.toString();
//			checking if such entry exists in the current ElasticNode
			Map<Character,ElasticNode> mapLocal=root.getMap();
			if(mapLocal.containsKey(letter)){
				return getSuggestions( truncName, initName,mapLocal.get(letter));
			}else {
				return null;
			}
			
		}
		
		return null;
	}
		
	}
	
	


