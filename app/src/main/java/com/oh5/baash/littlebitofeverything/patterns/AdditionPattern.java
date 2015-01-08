package com.oh5.baash.littlebitofeverything.patterns;

/**
 * Created by davidrawk on 7/01/15.
 */
public class AdditionPattern extends PatternsBase {
    private int m_variable1, m_variable2;
    public AdditionPattern(int max_variable){
        m_variable1 = m_randomiser.nextInt(max_variable);
        m_variable2 = m_randomiser.nextInt(max_variable);
        m_right_answer = m_variable1 + m_variable2;
    }

    public String [] get_answers(){
        if( m_answers == null) {
            m_answers = new String[3];
            int random_number;
            for(int x = 0; x < 3; x++){
                random_number = m_randomiser.nextInt(7)+1;
                if( m_randomiser.nextBoolean() ) {
                    random_number *= -1;
                }
                m_answers[x] = "" + (m_right_answer + random_number + 20)%20;
            }
            int right_index = m_randomiser.nextInt(3);
            m_answers[right_index] = "" + m_right_answer;
        }
        return m_answers;
    }

    public String[] get_puzzle(){
        if( m_pattern == null){
            m_pattern = new String[5];
            m_pattern[0] = "" + m_variable1;
            m_pattern[1] = "+";
            m_pattern[2] = "" + m_variable2;
            m_pattern[3] = "=";
            m_pattern[4] = get_right_answer();
        }
        return m_pattern;
    }

    public int get_size(){
        return 5;
    }
}
