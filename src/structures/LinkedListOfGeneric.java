package structures;

import java.util.Iterator;


public class LinkedListOfGeneric<E> implements Iterable<E>{

    // Classe interna Node
    private class Node {

        public E element;
        public Node next;

        public Node(E element) {
            this.element = element;
            next = null;
        }
        
        @SuppressWarnings("unused")
		public Node(E element, Node next) {
            this.element = element;
            this.next = next;
        }        
    }

    // Referência para o primeiro elemento da lista encadeada.
    private Node head;
    // Referência para o último elemento da lista encadeada.
    private Node tail;
    // Contador para a quantidade de elementos que a lista contem.
    private int count;

    /**
     * Construtor da lista
     */
    public LinkedListOfGeneric() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Adiciona um elemento ao final da lista
     *
     * @param element elemento a ser adicionado ao final da lista
     */
    public void add(E element) {
        Node aux = new Node(element);
        if (head == null) {
            head = aux;
        } else {
            tail.next = aux;
        }
        tail = aux;
        count++;
    }

    /**
     * Insere um elemento em uma determinada posicao da lista
     *
     * @param index a posicao da lista onde o elemento sera inserido
     * @param element elemento a ser inserido
     * @throws IndexOutOfBoundsException se (index < 0 || index > size())
     */
    public void add(int index, E element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node n = new Node(element);
        if (index == 0) { //insere no inicio
            n.next = head;
            head = n;
            if (tail == null) {
                tail = n;
            }
        } else if (index == count) { // insere no final
            tail.next = n;
            tail = n;
        } else { // insere no meio
            Node aux = head;
            for (int i = 0; i < index - 1; i++) {
                aux = aux.next;
            }
            n.next = aux.next;
            aux.next = n;
        }
        count++;
    }
    
    /**
     * Insere um elemento na primeira posi��o da lista
     * @param element
     */
    public void addFirst(E element) {
    	Node aux = new Node(element);
    	aux.next = head;
    	head = aux;
    }
    
    /**
     * Returns the first element of the list
     * 
     * @return element
     */
    public E getFirst() {
    	Node aux = head;
    	if (aux == null) {
    		return null;
    	}
    	return aux.element;
    }
    
    /**
     * Removes and returns the first element of the list
     * @return element
     */
    public E removeFirst() {
    	Node aux = head;
    	if (aux==null) {
    		return null;
    	}
    	E element = aux.element;
    	head = head.next;
    	return element;
    }

    /**
     * Retorna o elemento de uma determinada posicao da lista
     *
     * @param index a posição da lista
     * @return o elemento da posicao especificada
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public E get(int index) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = head;
        int c = 0;
        while (c < index) {
            aux = aux.next;
            c++;
        }
        return (aux.element);
    }

    /**
     * Substitui o elemento armanzenado em uma determinada posicao da lista pelo
     * elemento indicado
     *
     * @param index a posicao da lista
     * @param element o elemento a ser armazenado na lista
     * @return o elemento armazenado anteriormente na posicao da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public E set(int index, E element) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = head;
        for (int i = 0; i < index; i++) {
            aux = aux.next;
        }
        E tmp = aux.element;
        aux.element = element;
        return tmp;

    }

    /**
     * Remove a primeira ocorrencia do elemento na lista, se estiver presente
     *
     * @param element o elemento a ser removido
     * @return true se a lista contem o elemento especificado
     */
    public boolean remove(E element) {
        if (element == null) {
            return false;
        }
        if (count == 0) {
            return false;
        }

        if (head.element.equals(element)) { // remocao do primeiro
            head = head.next;
            if (count == 1) { // se havia so um elemento na lista
                tail = null;
            }
            count--;
            return true;
        }

        Node ant = head;
        Node aux = head.next;

        for (int i = 1; i < count; i++) {
            if (aux.element.equals(element)) {
                if (aux == tail) { // remocao do ultimo
                    tail = ant;
                    tail.next = null;
                } else { // remocao do meio
                    ant.next = aux.next;
                }
                count--;
                return true;
            }
            ant = ant.next;
            aux = aux.next;
        }

        return false;
    }

    /**
     * Retorna true se a lista nao contem elementos
     *
     * @return true se a lista nao contem elementos
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Retorna o numero de elementos da lista
     *
     * @return o numero de elementos da lista
     */
    public int size() {
        return count;
    }

    /**
     * Esvazia a lista
     */
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Remove o elemento de uma determinada posicao da lista
     *
     * @param index a posicao da lista
     * @return o elemento que foi removido da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public E removeByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }

        Node aux = head;
        if (index == 0) {
            if (tail == head) // se tiver apenas um elemento
            {
                tail = null;
            }
            head = head.next;
            count--;
            return aux.element;
        }
        int c = 0;
        while (c < index - 1) {
            aux = aux.next;
            c++;
        }
        E element = aux.next.element;
        if (tail == aux.next) {
            tail = aux;
        }
        aux.next = aux.next.next;
        count--;
        return element;
    }

    /**
     * Retorna o indice da primeira ocorrencia do elemento na lista, ou -1 se a
     * lista nao contem o elemento
     *
     * @param element o elemento a ser buscado
     * @return o indice da primeira ocorrencia do elemento na lista, ou -1 se a
     * lista nao contem o elemento
     */
    public int indexOf(E element) {
        int index = 0;
        Node aux = head;
        while (aux != null) {
            if (aux.element.equals(element)) {
                return (index);
            }
            aux = aux.next;
            index++;
        }
        return -1;
    }

    /**
     * Retorna true se a lista contem o elemento especificado
     *
     * @param element o elemento a ser testado
     * @return true se a lista contem o elemento especificado
     */
    public boolean contains(E element) {
        Node aux = head;
        while (aux != null) {
            if (aux.element.equals(element)) {
                return (true);
            }
            aux = aux.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        Node aux = head;

        while (aux != null) {
            s.append(aux.element.toString());
            s.append("\n");
            aux = aux.next;
        }

        return s.toString();
    }

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			Node current = head;
			
			@Override
			public boolean hasNext() 
			{
				return current!=null;
			}
			
			@Override
			public E next() 
			{
				if (hasNext())
				{
					E element = current.element;
					current = current.next;
					return element;
				}
				return null;
			}
			@Override
			public void remove()
			{
				throw new UnsupportedOperationException("Remove not yet implemented.");
			}
		};
	}
}
