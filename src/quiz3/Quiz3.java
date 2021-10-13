/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz3;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Burak Kaçmaz
 */
public class Quiz3 {

    
    public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
        int n = 0;
        boolean goodInput;
        System.out.print("Balon sayisini giriniz: ");

        do
        {
            try
            {
                n = scan.nextInt();
                while (n <= 0)
                {
                    System.out.print("Pozitif bir değer girin ");
                    n = scan.nextInt();
                }
                goodInput = true;
            }
            catch (InputMismatchException e)
            {
                System.out.print("Sayi degeri giriniz.");
                scan.nextLine();
                goodInput = false;
            }
        } while (!goodInput);


        BalonList balonlar = new BalonList();
        //balonpozisyonubul methodu ile yapamadigim icin bu sekilde yaptim.
        //verilen n degeri kadar balon olusturur.
        for (int i = 1; i <= n; i++)
            balonlar.addEnd(i);
        //k degeri girilerek kac tanede bir balon patlatilacagi hesaplanir.
        System.out.print("K degeri girin: ");
        int k = scan.nextInt();
        scan.close();
        System.out.print("Patlamadan once balonlar: ");
        System.out.print(balonlar.toString());
        System.out.println();
        
        
        balonlar.deleteSuitors(k);
        System.out.print("Patlamadan sona kalan balon: ");
        System.out.print(balonlar.toString());
    }
    
}

class BalonList extends CircularLinkedList<Integer>
{

    //Parametresiz constructor
    public BalonList()
    {
    }

    // Silinmesi gereken siradaki yeri bulur.
    private int findIndexOfLoser(int element)
    {
        Node<Integer> current = head;
        int index = 0;
        while (current.element != element)
        {
            current = current.next;
            index++;
        }
        return index;
    }

    //k-1 degeri kadar sayıp k sonraki degeri siler
    public void deleteSuitors(int k)
    {
        Node<Integer> current = head;
        while (size != 1)
        {
            for (int i = 1; i < k; i++)
            {
                current = current.next;
            }
            int deletionIndex = findIndexOfLoser(current.element);
            current = current.next;
            delete(deletionIndex);
        }
    }
}

   
class CircularLinkedList<E>
{

    //odevde verilen sekliyle yapamadım o yuzden bu sekilde yaptim
    protected Node<E> head = null;
    protected Node<E> tail = null;
    protected int size;

    public CircularLinkedList()
    {

    }

    // Circular Linked List'e eleman eklemek icin
    public void addEnd(E element)
    {
        if (size == 0)
        {
            head = tail = new Node<E>(element, head);
        }
        else
        {
            tail = tail.next = new Node<E>(element, head);
        }
        size++;
    }

    // silinecek dugumun silinmesi icin
    public void delete(int index)
    {
        if (index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException("Sinir asildi.");
        else if (index == 0)
            deleteFirst();
        else if (index == size - 1)
            deleteEnd();
        else
        {
            Node<E> previous = head;
            Node<E> current = head.next;
            for (int i = 1; i < index; i++)
            {
                previous = previous.next;
                current = current.next;
            }
            previous.next = current.next;
            current.next = null;
            size--;
        }
    }

    //  Sondan deger silmek icin
    public void deleteEnd()
    {
        if (size == 1)
        {
            head = tail;
            tail.next = null;
        } else if (size > 1)
        {
            Node<E> current = head;
            while (current.next != tail)
                current = current.next;
            tail.next = null;
            current.next = head;
            tail = current;
        }
        size--;
    }

    // Circular Linked List'teki ilk degeri silmek icin
    public void deleteFirst()
    {
        if (size == 1)
        {
            head = tail;
            tail.next = null;
        } else if (size > 1)
        {
            Node<E> current = head;
            head = head.next;
            current.next = null;
            tail.next = head;
        }
        size--;
    }

    // toString formatina cevirdim burada
    public String toString()
    {
        Node<E> current = head;
        StringBuilder balonlarList = new StringBuilder();

        if (size >= 1)
        {
            balonlarList.append(current.element);
            current = current.next;
        }
        for (int i = 1; i < size; i++)
        {
            balonlarList.append(" ").append(current.element.toString());
            current = current.next;
        }
        return balonlarList.toString();
    }
}

//  Node sinifi
class Node<E>
{
    //odevde istenen sekliyle yapamadigim icin bu sekilde yaptim.
    protected E element = null;
    protected Node<E> next = null;

    // parametresiz constructor
    public Node()
    {

    }

    // parametreli constructor
    public Node(E element, Node<E> next)
    {
        this.element = element;
        this.next = next;
    }

    public Node(E element)
    {
        this.element = element;
    }

    public Node(Node<E> next)
    {
        this.next = next;
    }
}
