import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ElasticNode {

	private char letter;
	private boolean isEnd;
	private Map<Character,ElasticNode> map=new HashMap<Character,ElasticNode>();
	private Set<String> relatedWordList=new HashSet<String>();
	private Map<Character,Set<String>> repoMap=new HashMap<Character,Set<String>>();
		
	public ElasticNode(){

	}

	public ElasticNode(char letter){
		this.letter=letter;
		
	}
	
	public ElasticNode(char letter, boolean isEnd){
		this.letter=letter;
		this.isEnd=isEnd;
	}
	
	public ElasticNode(char letter, boolean isEnd, HashMap map,HashSet relatedWordList){
		this.letter=letter;
		this.isEnd=isEnd;
		this.map=map;
		
		
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	


	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public Map<Character, ElasticNode> getMap() {
		return map;
	}

	public void setMap(Map<Character, ElasticNode> map) {
		this.map = map;
	}

	
	
	
	public Map<Character, Set<String>> getRepoMap() {
		return repoMap;
	}

	public void setRepoMap(Map<Character, Set<String>> repoMap) {
		this.repoMap = repoMap;
	}

	
	
	
	public Set<String> getRelatedWordList() {
		return relatedWordList;
	}

	public void setRelatedWordList(Set<String> relatedWordList) {
		this.relatedWordList = relatedWordList;
	}

	@Override
	public String toString() {
		return "ElasticNode [letter=" + letter + ", isEnd=" + isEnd + ", map=" + map + "]";
	}
	
	
	
}
