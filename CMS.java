// Comment Management System

import java.util.ArrayList;
import java.util.Scanner;

class Color {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
}

class StaticVars {
    static ArrayList<User> Users = new ArrayList<>();
    static ArrayList<Topic> Topics = new ArrayList<>();
}

class User extends StaticVars {
    int ID;
    String username;
    ArrayList<Comment> comments = new ArrayList<>();

    User(String name) {
        username = name;
        Users.add(this);
        this.ID = Users.indexOf(this) + 1;
    }
}

class Topic extends StaticVars {
    int ID;
    String heading;
    String desc;

    ArrayList<Comment> comments = new ArrayList<>();

    Topic(String heading, String desc) {
        this(heading);
        this.desc = desc;
    }

    Topic(String heading) {
        this.heading = heading;
        Topics.add(this);
        this.ID = Topics.indexOf(this) + 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\t" + Color.CYAN + this.heading + Color.RESET + "\n");
        sb.append(Color.GREEN + "======================" + Color.RESET);
        sb.append("\n\n" + desc + "\n");
        return sb.toString();
    }
}

class Comment extends StaticVars {
    int userID;
    int topicID;
    String value = "";

    Comment(User user, Topic topic, String value) {
        userID = user.ID;
        topicID = topic.ID;
        this.value = value;
        user.comments.add(this);
        topic.comments.add(this);
        // System.out.println("Comment Registed Successfully ....");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String name = Users.get(userID - 1).username;
        sb.append(" ");
        for (int i = 0; i <= name.length() + value.length() + 3; i++) {
            sb.append("-");
        }
        sb.append("\n| ");
        sb.append(name + ": " + value + " |\n");
        sb.append("|.");
        for (int i = 0; i <= name.length() + value.length() + 2; i++) {
            sb.append("-");
        }
        sb.append("\n");
        return sb.toString();
    }
}

public class CMS {

    static void clearScreen() throws Exception {
        Thread.sleep(900);
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void printCommentsOfUser(User user) {
        int lenOfLongestComment = 0;
        for (Comment c : user.comments) {
            if (c.value.length() > lenOfLongestComment) {
                lenOfLongestComment = c.value.length();
            }
        }
        lenOfLongestComment = Math.max(lenOfLongestComment, 9); // Minimum width for "COMMENTS"

        int lenOfLongestTopic = 0;
        for (Comment c : user.comments) {
            String heading = StaticVars.Topics.get(c.topicID - 1).heading;
            if (heading.length() > lenOfLongestTopic) {
                lenOfLongestTopic = heading.length();
            }
        }
        lenOfLongestTopic = Math.max(lenOfLongestTopic, 7); // Minimum width for "TOPIC"

        int commentColumnWidth = lenOfLongestComment + 2;
        int topicColumnWidth = lenOfLongestTopic + 2;

        // Top border
        System.out.print("\t|");
        for (int i = 0; i < commentColumnWidth; i++)
            System.out.print("=");
        System.out.print("|");
        for (int i = 0; i < topicColumnWidth; i++)
            System.out.print("=");
        System.out.println("|");

        // Headers
        String commentHeader = "COMMENTS";
        String topicHeader = "TOPIC";

        int commentLeft = (commentColumnWidth - commentHeader.length()) / 2;
        int commentRight = commentColumnWidth - commentHeader.length() - commentLeft;

        int topicLeft = (topicColumnWidth - topicHeader.length()) / 2;
        int topicRight = topicColumnWidth - topicHeader.length() - topicLeft;

        System.out.print("\t|");
        for (int i = 0; i < commentLeft; i++)
            System.out.print(" ");
        System.out.print(commentHeader);
        for (int i = 0; i < commentRight; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < topicLeft; i++)
            System.out.print(" ");
        System.out.print(topicHeader);
        for (int i = 0; i < topicRight; i++)
            System.out.print(" ");
        System.out.println("|");

        // Header bottom border
        System.out.print("\t|");
        for (int i = 0; i < commentColumnWidth; i++)
            System.out.print("=");
        System.out.print("|");
        for (int i = 0; i < topicColumnWidth; i++)
            System.out.print("=");
        System.out.println("|");

        // Comments and topics
        for (Comment c : user.comments) {
            String comment = c.value;
            String topic = StaticVars.Topics.get(c.topicID - 1).heading;

            int commentPadLeft = (commentColumnWidth - comment.length()) / 2;
            int commentPadRight = commentColumnWidth - comment.length() - commentPadLeft;

            int topicPadLeft = (topicColumnWidth - topic.length()) / 2;
            int topicPadRight = topicColumnWidth - topic.length() - topicPadLeft;

            System.out.print("\t|");
            for (int i = 0; i < commentPadLeft; i++)
                System.out.print(" ");
            System.out.print(comment);
            for (int i = 0; i < commentPadRight; i++)
                System.out.print(" ");
            System.out.print("|");
            for (int i = 0; i < topicPadLeft; i++)
                System.out.print(" ");
            System.out.print(topic);
            for (int i = 0; i < topicPadRight; i++)
                System.out.print(" ");
            System.out.println("|");
        }

        // Bottom border
        System.out.print("\t|");
        for (int i = 0; i < commentColumnWidth; i++)
            System.out.print("=");
        System.out.print("|");
        for (int i = 0; i < topicColumnWidth; i++)
            System.out.print("=");
        System.out.println("|");
    }

    static void menu(User user) throws Exception {
        Scanner sc = new Scanner(System.in);
        clearScreen();
        System.out.println(Color.RED + " Enter the topic no you want to navigate to: " + Color.RESET);
        for (Topic t : StaticVars.Topics) {
            System.out.println("\t" + t.ID + Color.CYAN + " " + t.heading + Color.RESET);
        }
        System.out.println("\t0" + Color.RED + " " + "EXIT" + Color.RESET);
        int topicID = sc.nextInt();
        sc.nextLine();
        try {
            if (topicID == 0) {
                clearScreen();
                System.out.println(Color.CYAN + "Comments Made By " + user.username + ":" + Color.RESET);
                printCommentsOfUser(user);
                return;
            }
            if (topicID > StaticVars.Topics.size() || topicID < 0) {
                System.out.println(Color.RED + "Enter valid no.... " + Color.RESET);
                return;
            }
        } catch (Exception e) {
            System.out.println(Color.RED + "Enter valid no.... " + Color.RESET);
            return;
        }
        clearScreen();
        printAllComments(user, topicID);
    }

    static void printAllComments(User user, int topicID) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println(StaticVars.Topics.get(topicID - 1));
        System.out.println(Color.GREEN + "\t============" + Color.RESET);
        System.out.println("\t" + Color.GREEN + "|" + Color.RESET + Color.RED + " COMMENTS " + Color.RESET
                + Color.GREEN + "|" + Color.RESET);
        System.out.println(Color.GREEN + "\t============" + Color.RESET);

        for (Comment c : StaticVars.Topics.get(topicID - 1).comments) {
            System.out.println(c);
        }
        System.out.print("\n\n" + Color.RED + "Write your Comment here: " + Color.RESET);
        String commentVal = sc.nextLine();
        // clearScreen();
        Thread.sleep(250);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        if (!commentVal.isEmpty()) {
            Comment userComment = new Comment(user, StaticVars.Topics.get(topicID - 1), commentVal);
            printAllComments(user, topicID);
        } else
            menu(user);
    }

    public static void main(String[] args) throws Exception {

        User user1 = new User("User1");
        User user2 = new User("User2");
        User user3 = new User("User3");
        User user4 = new User("User4");

        Topic topic1 = new Topic("Java",
                "Java is a high-level, general-purpose, memory-safe, object-oriented programming language. It is intended to let programmers write once, run anywhere (WORA), meaning that compiled Java code can run on all platforms that support Java without the need to recompile. Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of the underlying computer architecture.\n\n The syntax of Java is similar to C and C++, but has fewer low-level facilities than either of them. The Java runtime provides dynamic capabilities (such as reflection and runtime code modification) that are typically not available in traditional compiled languages.");

        Topic topic2 = new Topic("C",
                "C (pronounced /si/ - like the letter c) is a general-purpose programming language. It was created in the 1970s by Dennis Ritchie and remains very widely used and influential. By design, C's features cleanly reflect the capabilities of the targeted CPUs. It has found lasting use in operating systems code (especially in kernels), device drivers, and protocol stacks, but its use in application software has been decreasing. C is commonly used on computer architectures that range from the largest supercomputers to the smallest microcontrollers and embedded systems.");

        Topic topic3 = new Topic("C++",
                "C++ (/si pls pls/, pronounced \"C plus plus\" and sometimes abbreviated as CPP) is a high-level, general-purpose programming language created by Danish computer scientist Bjarne Stroustrup. First released in 1985 as an extension of the C programming language, it has since expanded significantly over time; as of 1997, C++ has object-oriented, generic, and functional features, in addition to facilities for low-level memory manipulation for systems like microcomputers or to make operating systems like Linux or Windows.\n\n It is usually implemented as a compiled language, and many vendors provide C++ compilers, including the Free Software Foundation, LLVM, Microsoft, Intel, Embarcadero, Oracle, and IBM.");

        Topic topic4 = new Topic("Python",
                "Python is a high-level, general-purpose programming language. Its design philosophy emphasizes code readability with the use of significant indentation.\n\nPython is dynamically type-checked and garbage-collected. It supports multiple programming paradigms, including structured (particularly procedural), object-oriented and functional programming. It is often described as a \"batteries included\" language due to its comprehensive standard library.\n\nGuido van Rossum began working on Python in the late 1980s as a successor to the ABC programming language and first released it in 1991 as Python 0.9.0. Python 2.0 was released in 2000. Python 3.0, released in 2008, was a major revision not completely backward-compatible with earlier versions. Python 2.7.18, released in 2020, was the last release of Python 2.\n\nPython consistently ranks as one of the most popular programming languages, and has gained widespread use in the machine learning community.");

        Comment c1 = new Comment(user1, topic1, "Is that so ?");
        Comment c2 = new Comment(user1, topic3, "What do you mean by general purpose ?");
        Comment c3 = new Comment(user2, topic2, "C, C, C, Sea?");
        Comment c4 = new Comment(user4, topic2, "#include<stdio.h> is the beginning of a C file");
        Comment c5 = new Comment(user4, topic4, "Python <3");
        Comment c6 = new Comment(user3, topic4, "|-> Replying to User4 : I <3 py too...");

        Scanner sc = new Scanner(System.in);
        System.out.print(Color.BLUE + "Enter your name: " + Color.RESET);
        String username = sc.nextLine();
        User user = new User(username);
        menu(user);
    }
}
