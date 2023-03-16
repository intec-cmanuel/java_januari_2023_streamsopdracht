package be.intecbrussel;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Stream;

public class BookApp {
    public static void main(String[] args) {
        Book[] books = getBookArray();

        System.out.println(getNewestBook(books));
        sep();
        printyoungestWriter(books);
        sep();
        printSortedByTitle(books);
        sep();
        countBooksPerAuthor(books);
        sep();
        printBooksReleasedIn2016(books);

    }

    private static Book[] getBookArray() {
        Person p1 = new Person("Jan", "Croquette", LocalDate.of(1981,6,7));
        Person p2 = new Person("Bruce", "Eckel", LocalDate.of(1957,8,8));
        Person p3 = new Person("Anas", "Nieuwkabas", LocalDate.of(2016,1,1));

        Book b1 = new Book("The book thief", p1, LocalDate.of(2003, 1,1), "Romance");
        Book b2 = new Book("Thinking in Java", p2, LocalDate.of(2016, 2, 19), "Nerd");
        Book b3 = new Book("The bible", p3, LocalDate.of(1,1,1), "Science-fiction");
        Book b4 = new Book("The kite runner", p3, LocalDate.of(1949, 3, 12), "Sport");
        Book b5 = new Book("Teaching at intec", p1, LocalDate.now(), "Horror");

        return new Book[]{b1,b2,b3,b4,b5};
    }

    private static void sep() {
        System.out.println("-".repeat(50));
    }

    public static Book getNewestBook(Book[] books) {
//        Book recentestBook = Stream.of(books)
//                .max(Comparator.comparing(book -> book.getReleaseDate())).get();
//        return recentestBook;

        Book[] sortedBooks = Stream.of(books)
                .sorted(Comparator.comparing(Book::getReleaseDate).reversed())
                .toArray(Book[]::new);

        return sortedBooks[0];
    }

    public static void printyoungestWriter(Book[] books ){
//        Person[] people = Stream.of(books)
//                .sorted(Comparator.comparing(book -> book.getAuthor().getDateOfBirth()))
//                .map(Book::getAuthor)
//                .toArray(Person[]::new);
//
//        System.out.println(people[people.length-1]);

        System.out.println(
                Stream.of(books)
                        .map(Book::getAuthor)
                        .max(Comparator.comparing(Person::getDateOfBirth))
                        .get()
        );
    }

    public static void printSortedByTitle(Book[] books) {
//        Stream.of(books)
//                .sorted(Comparator.comparing(item -> item.getTitle().toLowerCase()))
//                .forEach(System.out::println);

        Stream.of(books)
                .sorted(Book.comparatorByTitle())
                .forEach(System.out::println);
    }



    public static void countBooksPerAuthor (Book[] books) {
//        Stream.of(books)
//                .map(Book::getAuthor)
//                .distinct()
//                .forEach(author -> {
//                    System.out.print(author.getFirstName() + " " + author.getLastName() + ": ");
//                    long count = Stream.of(books)
//                            .filter(book -> book.getAuthor().equals(author))
//                            .count();
//                    System.out.println(count);
//                });

        Stream.of(books)
                .map(Book::getAuthor)
                .distinct()
                .peek(author -> System.out.print(author.getFirstName() + " " + author.getLastName() + ": "))
                .forEach(author -> System.out.println(
                        Stream.of(books)
                                .filter(book -> book.getAuthor().equals(author))
                                .count())
                );
//---------------------------------------------------------------
        Person[] authors = new Person[books.length];
        for (int i = 0; i < books.length; i++) {
            authors[i] = books[i].getAuthor();
        }

        int count = 0;
        for (int i = 0; i < authors.length; i++){
            Person p = authors[i];
            if (p != null) {
                count++;

                for (int j = i; j < authors.length; j++) {
                    if (authors[j] != null && authors[j].equals(p) ) {
                        authors[j] = null;
                    }
                }
            }

        }

        Person[] uniqueAuthors = new Person[count];
        int uniqueIndex = 0;

        for (Book book : books) {
            boolean isDuplicate = false;
            Person p = book.getAuthor();
            for (Person uniqueAuthor : uniqueAuthors) {
                if (uniqueAuthor == p) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                uniqueAuthors[uniqueIndex] = p;
                uniqueIndex++;
            }
        }

        for (Person uniqueAuthor : uniqueAuthors) {
            System.out.print(uniqueAuthor.getFirstName() + " " + uniqueAuthor.getLastName() + ": ");
            int amountOfBooks = 0;
            for (Book book : books) {
                if (book.getAuthor().equals(uniqueAuthor)) {
                    amountOfBooks++;
                }
            }
            System.out.println(amountOfBooks);
        }



    }

    public static void printBooksReleasedIn2016(Book[] books) {
//        Stream.of(books)
//                .filter(book -> book.getReleaseDate().getYear() == 2016)
//                .forEach(System.out::println);

        if (
                Stream.of(books)
                        .filter(book -> book.getReleaseDate().getYear() == 2016)
                        .peek(System.out::println)
                        .count()  == 0
        ) {
            System.out.println("No books released");
        }
    }
}
