package com.oh5.baash.littlebitofeverything.patterns;

/**
 * Created by davidrawk on 8/01/15.
 */
public class SubtractionPattern extends BasePattern {
    private int m_variable1, m_variable2;
    public SubtractionPattern(int max_variable){
        do {
            m_variable1 = m_randomiser.nextInt(max_variable) + 1;
            m_variable2 = m_randomiser.nextInt(max_variable) + 1;
            if (m_variable1 < m_variable2) {
                int temp = m_variable1;
                m_variable1 = m_variable2;
                m_variable2 = temp;
            }
            m_right_answer = m_variable1 - m_variable2;
        }while(m_variable1 == m_right_answer || m_variable2 == m_right_answer);
    }

    public String [] get_answers(){
        if( m_answers == null) {
            m_answers = new String[3];
            int [] answer_ints = get_answer_ints();
            for(int x = 0; x < 3; x++){
                m_answers[x] = "" + answer_ints[x];
            }
        }
        return m_answers;
    }

    public String[] get_puzzle(){
        if( m_pattern == null){
            m_pattern = new String[5];
            m_pattern[0] = "" + m_variable1;
            m_pattern[1] = "-";
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
