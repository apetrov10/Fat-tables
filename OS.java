import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OS implements ActionListener {

	JFrame wind = new JFrame("File Allocation Table");	
	JTable dirTable = new JTable(20,3);
	JTable fatTable = new JTable(20,2);
	JButton addFileBtn = new JButton("Добави файл");
	JButton delFileBtn = new JButton("Изтрий файл");
	JButton newBlockBtn = new JButton("Добави следващ блок");
	JTextField newBlockTxt = new JTextField();
	JLabel newBlockLabel = new JLabel("Позиция: ");
	JLabel remBlocks = new JLabel("Оставащи блокове: ");
	JLabel remBlocksNum = new JLabel("");
	JTextField addFileTxt = new JTextField();
	JLabel nameLabel = new JLabel("Име: ");
	JLabel blockLabel = new JLabel("Първи блок: ");
	JTextField addBlockTxt = new JTextField();
	JTextField addSizeTxt = new JTextField();
	JLabel sizeLabel = new JLabel("Размер: ");
	int blockCounter;
	int prevBlock;
	boolean frstBlock, rmngBlocks,cont;
	
	OS(){
		wind.setLayout(null);
		wind.setLocation(25, 25);
		wind.setSize(800, 600);
		wind.setVisible(true);
		wind.setResizable(false);
		wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dirTable.setBounds(50, 200, 250, 320);
		wind.add(dirTable);
		fatTable.setBounds(500, 200, 150, 320);
		wind.add(fatTable);
		addFileBtn.setBounds(120, 120, 120, 20);
		delFileBtn.setBounds(120, 150, 120, 20);
		wind.add(addFileBtn);	
		wind.add(delFileBtn);		
		addFileTxt.setBounds(120, 20, 95, 20);
		addBlockTxt.setBounds(120, 80, 95, 20);
		wind.add(addBlockTxt);
		wind.add(addFileTxt);
		addSizeTxt.setBounds(120, 50, 95, 20);
		wind.add(addSizeTxt);
		nameLabel.setBounds(60, 20, 95, 20);
		sizeLabel.setBounds(60, 50, 95, 20);
		blockLabel.setBounds(40, 80, 95, 20);
		wind.add(blockLabel);
		wind.add(nameLabel);
		wind.add(sizeLabel);
		newBlockBtn.setBounds(500, 150, 170, 20);
		wind.add(newBlockBtn);
		newBlockTxt.setBounds(560, 80, 95, 20);
		wind.add(newBlockTxt);
		newBlockLabel.setBounds(500, 80, 95, 20);
		wind.add(newBlockLabel);
		remBlocks.setBounds(500, 50, 120, 20);
		wind.add(remBlocks);
		remBlocksNum.setBounds(630, 50, 120, 20);
		wind.add(remBlocksNum);
		delFileBtn.addActionListener(this);
		addFileBtn.addActionListener(this);
		newBlockBtn.addActionListener(this);
		for (int i = 0; i<20; i++){
			dirTable.setValueAt(" ", i, 0);
			dirTable.setValueAt(" ", i, 2);
			fatTable.setValueAt(""+i, i, 0);
			fatTable.setValueAt(" ", i, 1);
		}
		
	}
	void SetDirectory(int Block){
		if(blockCounter==0){
			
		}
		else{
			
		
		for (int i = 0; i<20; i++){
			if(i == Block){
				if(fatTable.getValueAt(i, 1).equals(" ")){
					blockCounter--;
					remBlocksNum.setText(""+blockCounter);
					if (blockCounter <= 0){
						fatTable.setValueAt(""+i, prevBlock, 1);
						fatTable.setValueAt("Null", i, 1);
						rmngBlocks = false;
						
						prevBlock = Block;
					}
					else if(frstBlock==true){
						fatTable.setValueAt(" ", prevBlock, 1);
					}
					else{
						fatTable.setValueAt(""+i, prevBlock, 1);
						prevBlock = Block;
					}
				}
				else{
					JOptionPane.showMessageDialog(wind, "Блокът не е празен");
				}
				frstBlock = false;
			break;
			}
		}}
	}
	void SaveFile(String Name, int Size, int FirstBlock){
		if (blockCounter > 0){
			JOptionPane.showMessageDialog(wind, "Не са добавени всички блокове на файла!");
		}
		else{
			int counter = 0;
			
				for (int i = 0; i<20; i++){
					prevBlock = FirstBlock;
					if ((dirTable.getValueAt(i, 0)) == " "){
						
						dirTable.setValueAt(Name, i, 0);
						dirTable.setValueAt(Size, i, 1);
						dirTable.setValueAt(FirstBlock, i, 2);
						blockCounter = Size;
						
						SetDirectory(FirstBlock);
						break;
					}
					else{
						counter++;
					}
					if (counter==20){
						JOptionPane.showMessageDialog(wind, "Таблицата е пълна!");
					}
			}
			
			
		}
	}
	void DeleteDirectory(String firstBlock){
		String frstBlock;
		for (int i = 0; i<20; i++){
			if ((fatTable.getValueAt(i, 0)).equals(firstBlock)){
				if(firstBlock.equals("NULL")){
					fatTable.setValueAt(" ", i, 1);
				}
				else{
					frstBlock = fatTable.getValueAt(i, 1).toString();
					fatTable.setValueAt(" ", i, 1);
					DeleteDirectory(frstBlock);
				}
				
			}
		}
	}
	void DeleteFile(String Name){
		for (int i = 0; i<20; i++){
			if ((dirTable.getValueAt(i, 0)).equals(Name)){
				String frstBlock = dirTable.getValueAt(i, 2).toString();
				DeleteDirectory(frstBlock);
				dirTable.setValueAt(" ", i, 0);
				dirTable.setValueAt(" ", i, 1);
				dirTable.setValueAt(" ", i, 2);
				break;
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new OS();
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addFileBtn){
			rmngBlocks = true;
			try {
				Integer.parseInt(addSizeTxt.getText());
				Integer.parseInt(addBlockTxt.getText());
	        } catch (NumberFormatException m) {
	            //do something clever with the exception
	        	JOptionPane.showMessageDialog(wind, "Въведете валидни числа!");
	        }
			if((Integer.parseInt(addSizeTxt.getText())>20) | (Integer.parseInt(addBlockTxt.getText())>19)|(Integer.parseInt(addSizeTxt.getText())<1) | (Integer.parseInt(addBlockTxt.getText())<0)){
				JOptionPane.showMessageDialog(wind, "Въведете валидна стойност!");
			}else{
				int temp = Integer.parseInt(addSizeTxt.getText()) - 1;
				remBlocksNum.setText(""+temp);
				frstBlock = true;
				for (int j = 0; j<20; j++){
					if ((dirTable.getValueAt(j, 2)).toString().equals(""+Integer.parseInt(addSizeTxt.getText()))){
						
						blockCounter = 0;
						cont=false;
						remBlocksNum.setText("0");
						JOptionPane.showMessageDialog(wind, "Блокът не е празен!");
						break;
					}
					else{
						cont=true;
					}
				}
				if(cont==true){			
					SaveFile(addFileTxt.getText(), Integer.parseInt(addSizeTxt.getText()),Integer.parseInt(addBlockTxt.getText()));
				}
			}
		}
		else if (e.getSource() == delFileBtn){
			DeleteFile(addFileTxt.getText());
		}
		else if (e.getSource() == newBlockBtn){
			if (rmngBlocks==false){
				JOptionPane.showMessageDialog(wind, "Първо въведете файл!");
			}
			else{
				try {
					Integer.parseInt(newBlockTxt.getText());
				}
				catch (NumberFormatException m) {
		            //do something clever with the exception
		        	JOptionPane.showMessageDialog(wind, "Въведете валидни числа!");
		        }
				if((Integer.parseInt(newBlockTxt.getText())>19)|(Integer.parseInt(newBlockTxt.getText())<0)){
					JOptionPane.showMessageDialog(wind, "Въведете валидна стойност!");
				}else{
					SetDirectory(Integer.parseInt(newBlockTxt.getText()));
				}
			}
			
		}
		
	}

}
