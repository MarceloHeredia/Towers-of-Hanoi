package towerofHanoi;

@SuppressWarnings("serial")
public class GameException extends Exception
{
	String erro;
	public GameException(String erro)
	{
		this.erro = erro;
	}
	public String toString()
	{
		return "\n\n\nErro: "+erro+".";
	}
}
