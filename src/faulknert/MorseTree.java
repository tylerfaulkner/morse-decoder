/*
 * Course: CS2852-071
 * Spring 2020
 * Lab 7
 * Name: Sean Jones
 * Created: 04/30/20
 */
package faulknert;

import java.io.Serializable;

/**
 * Creates a data tree of morse code values with there associated symbols.
 * @param <E>
 */
public class MorseTree<E> {
    /**
     * Inner Node class to be used with morse tree
     *
     * @param <E> - The type matching the Binary Tree class
     */
    private static class Node<E> implements Serializable {
        private static final long serialVersionUID = 0;
        protected E data;
        protected Node<E> dot;
        protected Node<E> dash;

        public Node(){
            this(null, null, null);
        }

        public Node(E data) {
            this(data, null, null);
        }

        public Node(E data, Node<E> dot, Node<E> dash) {
            this.data = data;
            this.dot = dot;
            this.dash = dash;
        }
    }

    protected final Node<E> root;

    /**
     * Default initialization method.
     */
    public MorseTree() {
        this.root = new Node<E>(null);
    }

    /**
     * Adds a new node to the tree.
     * @param symbol the english translation of the morse code
     * @param code morse code string corresponding to the symbol
     */
    public void add(E symbol, String code){
        addCode(symbol, code, root);
    }

    /**
     * Takes the morse code string and returns it's english value.
     * @param code morse code
     * @return english translation
     */
    public E decode(String code){
        return decoder(code, root);
    }

    private void addCode(E symbol, String codeToFind, Node<E> node){
        if(codeToFind.length() == 0){
            node.data = symbol;
        } else {
            char code = codeToFind.charAt(0);
            String nextCode = codeToFind.substring(1);
            if (code == '.') {
                if (node.dot == null) {
                    node.dot = new Node<E>();
                }
                addCode(symbol, nextCode, node.dot);
            }
            if (code == '-') {
                if (node.dash == null) {
                    node.dash = new Node<E>();
                }
                addCode(symbol, nextCode, node.dash);
            }
        }
    }

    private E decoder(String code, Node<E> node){
        if (code.length() == 0){
            return node.data;
        }
        String nextCode = code.substring(1);
        if (code.charAt(0) == '.'){
            return decoder(nextCode, node.dot);
        } else if (code.charAt(0) == '-'){
            return decoder(nextCode, node.dash);
        } else {
            throw new IllegalArgumentException("Morse code can only consist of dots and dashes.");
        }
    }
}
