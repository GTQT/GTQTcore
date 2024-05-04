package keqing.gtqtcore.api.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Chat Calculator Helper
 *
 * @author Tech22
 *
 * <p>
 *     Based on Tech22's work <a href="https://github.com/TechLord22/chat-calculator">Chat Calculator</a>.
 *     This mod now public archive (based on MIT License), so I redo it in gtqtcore.
 * </p>
 */
@SuppressWarnings("all")
public class ChatCalculatorHelper {

    public static double eval(final String str, final EntityPlayer player) {
        return new Object() {
            private int pos = -1;
            private char ch;

            private void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : (char) -1;
            }

            private boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            private double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length())
                    throw new RuntimeException(new TextComponentTranslation("gtqtcore.chat_calculator.error.unexpected_char", str.charAt(pos)).getFormattedText());
                return x;
            }

            /**
             * Grammar:
             * expression = term | expression `+` term | expression `-` term.
             * term = factor | term `*` factor | term `/` factor | term `%` factor.
             * factor = `+` factor | `-` factor | `(` expression `)` | number
             *        | functionName `(` expression `)` | functionName factor
             *        | factor `^` factor
             */
            private double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+'))
                        x += parseTerm(); // addition
                    else if (eat('-'))
                        x -= parseTerm(); // subtraction
                    else
                        return x;
                }
            }

            private double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*'))
                        x *= parseFactor(); // multiplication
                    else if (eat('/'))
                        x /= parseFactor(); // division
                    else if (eat('%'))
                        x %= parseFactor(); // modulus
                    else
                        return x;
                }
            }

            private double parseFactor() {
                if (eat('+'))
                    return +parseFactor(); // unary plus
                if (eat('-'))
                    return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;

                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')'))
                        throw new RuntimeException(new TextComponentTranslation("chatcalculator.error.parenthesis_end").getFormattedText());
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (eat('x')) {
                    x = player.getPosition().getX();
                } else if (eat('y')) {
                    x = player.getPosition().getY();
                } else if (eat('z')) {
                    x = player.getPosition().getZ();
                } else if (eat('e')) {
                    x = Math.E;
                } else if (eat('p') && eat('i')) {
                    x = Math.PI;
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z')
                        nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')'))
                            throw new RuntimeException(new TextComponentTranslation("gtqtcore.chat_calculator.error.parenthesis_func", func).getFormattedText());
                    } else {
                        x = parseFactor();
                    }
                    x = switch (func) {
                        case "sqrt" -> Math.sqrt(x);
                        case "log" -> Math.log10(x);
                        case "ln" -> Math.log(x);
                        case "sin" -> Math.sin(Math.toRadians(x));
                        case "cos" -> Math.cos(Math.toRadians(x));
                        case "tan" -> Math.tan(Math.toRadians(x));
                        case "asin" -> Math.asin(Math.toRadians(x));
                        case "acos" -> Math.acos(Math.toRadians(x));
                        case "atan" -> Math.atan(Math.toRadians(x));
                        default -> throw new RuntimeException(new TextComponentTranslation("gtqtcore.chat_calculator.error.unknown_func", func).getFormattedText());
                    };
                } else {
                    throw new RuntimeException(new TextComponentTranslation("gtqtcore.chat_calculator.error.unexpected_char", str.charAt(str.length() - 1)).getFormattedText());
                }

                if (eat('^'))
                    x = Math.pow(x, parseFactor()); // exponentiation
                return x;
            }
        }.parse();
    }
}