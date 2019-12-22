package compress.implementation;


import java.util.*;

public class HuffmanTextDictionary implements HuffmanDictionary<Character, String> {

    @Override
    public Map<Character, byte[]> compute(String s) {

        Map<Character, Integer> numberOfOccurrences = new HashMap<>();
        Map<Character, byte[]> dictionary = new HashMap<>();
        Map<Character, Float> probabilitiesOfOccurrences = new HashMap<>();
        List<List<Node>> graph = new ArrayList<>(new ArrayList<>());
        List<Node> firstRow = new ArrayList<>();


        s.chars().forEach(t-> numberOfOccurrences.merge((char) t, 1, Integer::sum));
        numberOfOccurrences.forEach( (key, value) -> probabilitiesOfOccurrences.put(key, (float)value/s.length()) );

        probabilitiesOfOccurrences.forEach((key,value) -> firstRow.add(new Node(value, null, key)));
        graph.add(firstRow);

        boolean notFinish = true;

        while(notFinish) {
            List<Node> lastRow = graph.get(graph.size() - 1);
            Collections.sort(lastRow, (a, b) -> Float.compare(a.getCurrentValue(), b.getCurrentValue()));
            if (lastRow.size() == 1)
                notFinish = false;
            else {
                List<Node> nextRow = new ArrayList<>();
                nextRow.add(new Node(lastRow.get(0).getCurrentValue() + lastRow.get(1).getCurrentValue(),
                                        Arrays.asList(lastRow.get(0),lastRow.get(1)), null));
                for(int i=2; i < lastRow.size(); i++)
                    nextRow.add(new Node(lastRow.get(i).getCurrentValue(), Arrays.asList(lastRow.get(i)), null));
                graph.add(nextRow);
            }
        }

        startCreatingBytes(graph);
        graph.get(0).forEach(t->dictionary.put(t.getLetter(), getByteArray(t)));
        return  dictionary;

    }

    private void startCreatingBytes(List<List<Node>> graph) {
        Node node1 = graph.get(graph.size()-1).get(0).getParents().get(0);
        Node node2 = graph.get(graph.size()-1).get(0).getParents().get(1);
        if(Float.compare(node1.getCurrentValue(), node2.getCurrentValue()) >= 0){
            node1.setBitsValues("1");
            node2.setBitsValues("0");
        }
        else{
            node1.setBitsValues("0");
            node2.setBitsValues("1");
        }
        parcurgere(node1);
        parcurgere(node2);
    }

    private byte[] getByteArray(Node node){
        char[] letters = node.getBitsValues().toCharArray();
        byte[] byteArray = new byte[letters.length];
        int i=0;
        for (char c: letters) {
            if(c == '1')
                byteArray[i++] = 1;
            else
                byteArray[i++] = 0;
        }
        return byteArray;
    }

    private void parcurgere(Node node){

         if(node.getParents() == null)
                return;
             else if(node.getParents().size() == 2){
                Node node1 = node.getParents().get(0);
                Node node2 = node.getParents().get(1);
                if(Float.compare(node1.getCurrentValue(),node2.getCurrentValue()) > 0){
                    node1.setBitsValues(node.getBitsValues() + "1");
                    node2.setBitsValues(node.getBitsValues() + "0");
                }
                else{
                    node1.setBitsValues(node.getBitsValues() + "0");
                    node2.setBitsValues(node.getBitsValues() + "1");
                }
                parcurgere(node1);
                parcurgere(node2);
    }
            else if( node.getParents().size() == 1){
                Node node1 = node.getParents().get(0);
                node1.setBitsValues(node.getBitsValues());
                parcurgere(node1);
         }
    }
}

class Node {
    private float currentValue;
    private String bitsValues;
    private List<Node> parents;
    private Character letter;

    public Node(float currentValue, List<Node> parents, Character letter) {
        this.currentValue = currentValue;
        this.parents = parents;
        this.letter = letter;
    }

    public Character getLetter() {
        return letter;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public String getBitsValues() {
        return bitsValues;
    }

    public void setBitsValues(String bitsValues) {
        this.bitsValues = bitsValues;
    }

    public List<Node> getParents() {
        return parents;
    }

    @Override
    public String toString() {
        return String.valueOf(currentValue);
    }
}