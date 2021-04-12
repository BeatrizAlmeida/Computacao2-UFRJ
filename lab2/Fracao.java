package lab2;

/**
 * Representa uma fra��o de forma expl�cita, guardando numerador e denominador inteiros.
 * Fra��es equivalentes (matematicamente) devem ser consideradas equals().
 */
public class Fracao {
	
	private int numerador, denominador;
	private boolean sinal;

    /**
     * Cria uma fra��o, baseada em seu numerador e denominador.
     * O sinal da fra��o ser� inferido a partir do sinal
     * do numerador e do denominador.
     *
     * @param numerador o numerador
     * @param denominador o denominador
     */
    public Fracao(int numerador, int denominador) {

        if (denominador == 0) {
            throw new ArithmeticException("Denominador n�o pode ser zero!!");
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
     * Retorna o sinal da fra��o.
     *
     * @return true, se for positiva ou nula (zero);
     *         false, se for negativa.
     */
    public boolean getSinal() {
        return this.sinal;   
    }

    /**
     * Retorna o (valor absoluto do) numerador desta fra��o, ou seja, sempre n�o-negativo
     * @return o numerador
     */
    public int getNumerador() {
        return this.numerador;   
    }

    /**
     * Retorna o (valor absoluto do) denominador desta fra��o, ou seja, sempre positivo
     * @return o numerador
     */
    public int getDenominador() {
        return this.denominador; 
    }

    /**
     * Retorna o valor num�rico da fra��o (com o sinal correto).
     *
     * @return um float, com o valor na melhor precis�o poss�vel
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
     * Retorna uma fra��o equivalente � esta fra��o,
     * e que n�o pode mais ser simplificada (irredut�vel).
     *
     * @return um outro objeto Fracao, se esta fra��o for redut�vel;
     *         esta pr�pria fra��o (this), se ela j� for irredut�vel
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