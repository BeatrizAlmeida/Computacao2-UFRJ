package lab2;

/**
 * Representa uma fração de forma explícita, guardando numerador e denominador inteiros.
 * Frações equivalentes (matematicamente) devem ser consideradas equals().
 */
public class Fracao {
	
	private int numerador, denominador;
	private boolean sinal;

    /**
     * Cria uma fração, baseada em seu numerador e denominador.
     * O sinal da fração será inferido a partir do sinal
     * do numerador e do denominador.
     *
     * @param numerador o numerador
     * @param denominador o denominador
     */
    public Fracao(int numerador, int denominador) {

        if (denominador == 0) {
            throw new ArithmeticException("Denominador não pode ser zero!!");
        }
        if((denominador<0 && numerador<0) || (numerador>0 && denominador>0) || numerador==0) {
        	this.sinal = true;
        }else {
        	this.sinal = false;
        }
        this.numerador = Math.abs(numerador);
        this.denominador = Math.abs(denominador);

    }

    /**
     * Retorna o sinal da fração.
     *
     * @return true, se for positiva ou nula (zero);
     *         false, se for negativa.
     */
    public boolean getSinal() {
        return this.sinal;   
    }

    /**
     * Retorna o (valor absoluto do) numerador desta fração, ou seja, sempre não-negativo
     * @return o numerador
     */
    public int getNumerador() {
        return this.numerador;   
    }

    /**
     * Retorna o (valor absoluto do) denominador desta fração, ou seja, sempre positivo
     * @return o numerador
     */
    public int getDenominador() {
        return this.denominador; 
    }

    /**
     * Retorna o valor numérico da fração (com o sinal correto).
     *
     * @return um float, com o valor na melhor precisão possível
     *         Ex.: -1/3 vai retornar 0.33333333
     */
    public float getValorNumerico() {
    	
    	float valorNumerico;
    	valorNumerico = (float)this.numerador/this.denominador;
    	if(this.sinal == false) {
    		valorNumerico = 0-valorNumerico;
    	}
        return valorNumerico;   
    }

    /**
     * Retorna uma fração equivalente à esta fração,
     * e que não pode mais ser simplificada (irredutível).
     *
     * @return um outro objeto Fracao, se esta fração for redutível;
     *         esta própria fração (this), se ela já for irredutível
     */
    public Fracao getFracaoGeratriz() {
    	int mdc = AritmeticaUtils.mdc(numerador, denominador);
    	if(mdc == 1) {
    		return this;
    	}
    	Fracao fracao = new Fracao(numerador/mdc, denominador/mdc);
        return fracao;   
    }

    @Override
    public String toString() {
    	String fracao = this.numerador + "/" + this.denominador;
    	if(this.numerador == 0) {
    		fracao = "0";
    	}
    	if(this.denominador == 1) {
    		fracao = ""+this.numerador;
    	}
        return this.sinal==true?fracao:"-"+fracao;   
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fracao fracao = (Fracao) o;
        fracao = fracao.getFracaoGeratriz();
        Fracao instancia = this.getFracaoGeratriz(); 
        return instancia.getNumerador() == fracao.getNumerador() &&
        		instancia.getDenominador() == fracao.getDenominador() &&
        		instancia.getSinal() == fracao.getSinal();
    	
    }
}