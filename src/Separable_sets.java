/**
 * Created by Magdalena Polak on 18.05.2016.
 */
public class Separable_sets {

    // Realizacja lasu z równoważeniem wysokości drzew.
   static class Elem {
        Object val;
        Elem parent;
        int height;
        public Elem(Object o)
        {
            this.val = o;
        }
    }
    void put(Object val, Elem parent)
    {

    }
    void MAKE_SET(Elem x) {
        x.parent = x;
        x.height = 0;
    }

    void UNION(Elem x, Elem y) {
        LINK(FIND(x), FIND(y));
    }

    void LINK(Elem x, Elem y) {
        if (x.height > y.height) y.parent = x;
        else {
            x.parent = y;
            if (x.height == y.height)
                y.height++;
        }
    }

        Elem FIND (Elem x)
        {
            return x == x.parent ? x : FIND(x.parent);
        }

    // Realizacja lasu z  kompresją ścieki.
        class Elem2 {
            Object val;
            Elem parent;
        }

    void MAKE_SET2(Elem x) {
        x.parent = x;
    }

    void UNION2(Elem x, Elem y) {
        LINK2(FIND2(x), FIND2(y));
    }

    void LINK2(Elem x, Elem y) {
        y.parent = x;
    }


    Elem FIND2(Elem x) {
        if (x != x.parent) x.parent = FIND2(x.parent);
        return x.parent;
    }
    public  void print(Elem x)
    {
        while(x != x.parent)
        {
            System.out.println(x.val);
            x = x.parent;

        }
        System.out.println(x.val);

    }

    public static void main (String[]args) {
        System.out.println("\n*********Równowazone zbiory rozłaczne***********");
        Separable_sets s = new Separable_sets();
        Elem e = new Elem(7);
        Elem e1 = new Elem(3);
        Elem e2= new Elem(5);
        Elem e3 = new Elem(9);
        Elem e4 = new Elem(4);
        Elem e5 = new Elem(11);
        s.MAKE_SET(e);
        s.MAKE_SET(e1);
        s.MAKE_SET(e2);
        s.MAKE_SET(e3);
        s.LINK(e1, e);
        s.LINK(e2, e1);
        s.LINK(e4, e3);
        s.LINK(e5, e2);
       // s.LINK(e1, e2);
       // s.LINK(e2, e3);

       System.out.println("wydruk od e5: "); s.print(e5);
        System.out.println("*****");
        System.out.println(s.FIND(e5).val);
        System.out.println(s.FIND(e1).val);

        s.UNION(e4, e1);
        System.out.println("wydruk od e4 po union: "); s.print(e4);
        System.out.println("Po połączeniu: " +s.FIND(e4).val);



        System.out.println("\n*********Z kompresją zbiory rozłaczne***********");
        Separable_sets z = new Separable_sets();
        Elem f = new Elem(7);
        Elem f1 = new Elem(3);
        Elem f2= new Elem(5);
        Elem f3 = new Elem(11);
        Elem f4 = new Elem(9);
        Elem f5 = new Elem(4);
        z.MAKE_SET2(f);
        z.MAKE_SET2(f1);
        z.MAKE_SET2(f2);
        z.MAKE_SET2(f4);
        z.MAKE_SET2(f5);
        z.LINK2(f, f1);
        z.LINK2(f1, f2);
        z.LINK2(f2, f3);

        z.LINK2(f4, f5);
        System.out.println(z.FIND2(f4).val);


        System.out.println(z.FIND2(f2).val);
      //  System.out.println(z.FIND2(f).val);

       z.UNION2(f, f5);

        System.out.println("Po połączeniu: " +z.FIND2(f).val);
    }

  }


