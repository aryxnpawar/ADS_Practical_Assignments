import java.util.*;
import java.util.Stack;

public class InfixToPostfix {
    private int precedence(char c){
        return switch (c){
            case '(',')' -> 0;
            case '+','-' -> 1;
            case '*','/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    /*private int precedence(char c) {
        switch (c) {
            case ')':
            case '(':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }*/

    private boolean isRightPrecedence(char c) {
        return c == '^';
    }

    public String convert(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else {
                if (c == '(')
                    stack.push(c);
                else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        result.append(stack.pop());
                    }
                    if (!stack.isEmpty() && stack.peek() == '(') {
                        stack.pop();
                    }

                } else {
                    if (stack.isEmpty())
                        stack.push(c);

                    else if (precedence(c) > precedence(stack.peek()))
                        stack.push(c);
                    else {
//                        if(isRightPrecedence(c))
//                            stack.push(c);
                        //else{
                        while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                            result.append(stack.pop());
                        }
                        stack.push(c);
                        //}
                    }
                }

            }

        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    public static void main(String[] args) {
        InfixToPostfix converter = new InfixToPostfix();
        String exp = "a+b*(c-d)";
        String postFixForm = converter.convert(exp);
        System.out.println(postFixForm);
    }
}
