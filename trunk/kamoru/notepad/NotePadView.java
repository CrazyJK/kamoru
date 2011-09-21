import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.*;

public class NotePadView extends JFrame implements ItemListener {
	NotePadControler notePadControler;
	// 변수 선언부
	JMenuBar menuBar;
	JMenu fileMenu, editMenu, formMenu, viewMenu, helpMenu, menus[];
	JMenuItem newFile, openFile, saveFile, saveAsFile, exitFile,
			fileMenuItems[];
	JMenuItem undo, cut, copy, paste, delete, find, nextFind, replace,
			selectAll, editMenuItems[];
	JCheckBoxMenuItem lineFit;
	JMenuItem font, formMenuItems[];
	JCheckBoxMenuItem statusBar;
	JMenuItem viewMenuItems[];
	JMenuItem helper, about, helpMenuItems[];
	JTextArea taMain;
	JPanel pStatusBar;
	JLabel lStatusBar;
	JScrollPane scrollPane;
	FileMenuView fileMenuDialog;
	FindDialog findDialog;
	ReplaceDialog replaceDialog;
	FontStyleView fontStyleView;

	// 생성자
	NotePadView(NotePadControler notePadControler) {
		this.notePadControler = notePadControler;
		// 배치관리자
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		setTitle("이상한  메모장");
		// 메뉴바 세팅
		menuBar = new JMenuBar();
		fileMenu = new JMenu("파일");
		editMenu = new JMenu("편집");
		formMenu = new JMenu("서식");
		viewMenu = new JMenu("보기");
		helpMenu = new JMenu("도움말");
		menus = new JMenu[] { fileMenu, editMenu, formMenu, viewMenu, helpMenu };

		// 파일메뉴 세팅
		newFile = new JMenuItem("새로만들기");
		openFile = new JMenuItem("열기");
		saveFile = new JMenuItem("저장");
		saveAsFile = new JMenuItem("다른  이름으로  저장");
		exitFile = new JMenuItem("끝내기");
		fileMenuItems = new JMenuItem[] { newFile, openFile, saveFile,
				saveAsFile, exitFile };

		fileMenu.add(newFile);
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(saveAsFile);
		fileMenu.addSeparator();
		fileMenu.add(exitFile);

		// 편집메뉴 세팅
		undo = new JMenuItem("실행취소");
		cut = new JMenuItem("잘라내기");
		copy = new JMenuItem("복사");
		paste = new JMenuItem("붙여넣기");
		delete = new JMenuItem("삭제");
		find = new JMenuItem("찾기");
		nextFind = new JMenuItem("다음찾기");
		replace = new JMenuItem("바꾸기");
		selectAll = new JMenuItem("모두선택");
		editMenuItems = new JMenuItem[] { undo, cut, copy, paste, delete, find,
				nextFind, replace, selectAll };

		editMenu.add(undo);
		editMenu.addSeparator();
		editMenu.add(cut);
		editMenu.add(copy);
		editMenu.add(paste);
		editMenu.add(delete);
		cut.setEnabled(false);
		copy.setEnabled(false);
		paste.setEnabled(false);
		delete.setEnabled(false);
		editMenu.addSeparator();
		editMenu.add(find);
		editMenu.add(nextFind);
		editMenu.add(replace);
		editMenu.addSeparator();
		editMenu.add(selectAll);

		// 서식메뉴 세팅
		lineFit = new JCheckBoxMenuItem("자동  줄  바꿈");
		font = new JMenuItem("글꼴");
		formMenuItems = new JMenuItem[] { lineFit, font };

		formMenu.add(lineFit);
		formMenu.add(font);

		// 보기메뉴 세팅
		statusBar = new JCheckBoxMenuItem("상태표시줄");
		viewMenuItems = new JMenuItem[] { statusBar };

		viewMenu.add(statusBar);

		// 도움말메뉴 세팅

		helper = new JMenuItem("도움말");
		about = new JMenuItem("정보");
		helpMenuItems = new JMenuItem[] { helper, about };

		helpMenu.add(helper);
		helpMenu.addSeparator();
		helpMenu.add(about);

		// 메뉴바 세팅
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(formMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		// 상태바 세팅
		pStatusBar = new JPanel();
		pStatusBar.setLayout(new BorderLayout());
		lStatusBar = new JLabel();
		lStatusBar.setHorizontalAlignment(JLabel.RIGHT);
		pStatusBar.add(lStatusBar);
		con.add(pStatusBar, "South");
		pStatusBar.setVisible(false);

		// 메뉴의 구성요소에 리스너를 부착하는 메소드를 호출
		setJMenuBar(menuBar);

		// TextArea 세팅
		taMain = new JTextArea(
				"<<안되는  기능>>\n<파일메뉴>\n\t새로만들기\n\t다른이름으로  저장\n<편집메뉴>\n\t실행취소\n<서식메뉴>\n\t자동줄바꿈\n<도움말메뉴>\n\t정보\n");
		scrollPane = new JScrollPane(taMain);
		con.add(scrollPane, "Center");

		setMenuBarItems();

		// 키입력을 받으면 이벤트 리스너를 호출
		taMain.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				somethingEventRaise();
			}
		});
		// 마우스 입력을 받으면 이벤트 리스너를 호출
		taMain.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				somethingEventRaise();
			}
		});
		// Text Area에서 Foucus가 잃을때 이벤트 리스너를 호출
		taMain.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				somethingEventRaise();
			}
		});

		// 끄기
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				dispose();
				System.exit(0);
			}
		});
	}

	// 메뉴에 리스너를 생성
	public void setMenuBarItems() {
		for (JMenuItem fileMenuItem : fileMenuItems) {
			fileMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent fileMenuActionEvent) {
					notePadControler.fileMenuHander(fileMenuActionEvent
							.getActionCommand());
				}

			});
		}
		for (JMenuItem editMenuItem : editMenuItems) {
			editMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent editMenuActionEvent) {
					notePadControler.editMenuHander(editMenuActionEvent
							.getActionCommand());
					somethingEventRaise();
				}

			});
		}
		for (JMenuItem formMenuItem : formMenuItems) {
			formMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent formMenuActionEvent) {
					notePadControler.formMenuHander(formMenuActionEvent
							.getActionCommand());
				}

			});
		}
		for (JMenuItem viewMenuItem : viewMenuItems) {
			viewMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent viewMenuActionEvent) {

				}

			});
		}
		for (JMenuItem helpMenuItem : helpMenuItems) {
			helpMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent helpMenuActionEvent) {

				}

			});
		}
		// 자동줄바꿈과 상태표시줄의 리스너
		lineFit.addItemListener(this);
		statusBar.addItemListener(this);

	}

	// 입력이 들어올때마다 버퍼체크, 현재텍스트창 블록설정여부, 현재 커서 위치를 체크함
	public void somethingEventRaise() {
		notePadControler.checkBufferd();
		notePadControler.blockSelected(taMain.getSelectedText());
		notePadControler.checkCursor(taMain.getText().substring(0,
				taMain.getCaretPosition()));
	}

	// 현재 좌표 출력
	public void setTextStatusBar(int[] coodinate) {
		lStatusBar.setText("줄  :  " + coodinate[0] + "    칸  :  "
				+ coodinate[1] + "    ");
	}

	// 현재 필드내에 셀렉트 된 부분 유무에 따라 편집메뉴중 일부분을 활성화/비활성화
	public void setHasBlocked() {
		cut.setEnabled(true);
		copy.setEnabled(true);
		delete.setEnabled(true);
	}

	public void setHasNotBloked() {
		cut.setEnabled(false);
		copy.setEnabled(false);
		delete.setEnabled(false);
	}

	// 현재 클립보드에 내용 유무에 따라 편집메뉴중 일부분을 활성화/비활성화
	public void setHasBufferd() {
		paste.setEnabled(true);
	}

	public void setHasNotBufferd() {
		paste.setEnabled(false);
	}

	// 상태표시줄의 활성화/비활성화 부분
	public void enableStatusBar() {
		pStatusBar.setVisible(true);
		somethingEventRaise();
		Dimension nowSize = this.getSize();
		pack();
		setSize(nowSize);
	}

	public void disableStatusBar() {
		pStatusBar.setVisible(false);
		Dimension nowSize = this.getSize();
		pack();
		setSize(nowSize);
	}

	// 상태표시줄 항목의 체크 여부 판별
	public void itemStateChanged(ItemEvent e) {
		System.out.println("되남?");
		if (((JCheckBoxMenuItem) e.getSource()).equals(statusBar)) {
			if (((JCheckBoxMenuItem) e.getSource()).getState()) {
				enableStatusBar();
			} else {
				disableStatusBar();
			}
		}
	}

	// 파일메뉴
	public void newFile() {

	};

	public void openFile() {
		fileMenuDialog = new FileMenuView("open");
		fileMenuDialog.setBounds(650, 450, 300, 250);
		fileMenu.setVisible(true);
	}

	public void saveFile() {
		fileMenuDialog = new FileMenuView("save");
		fileMenuDialog.setBounds(650, 450, 300, 250);
		fileMenu.setVisible(true);
	}

	public void saveAsFile() {

	}

	public void exit() {
		dispose();
		System.exit(0);
	}

	// 편집메뉴
	public void cut() {
		taMain.cut();
	}

	public void copy() {
		taMain.copy();
	}

	public void find() {
		findDialog = new FindDialog(NotePadView.this);
	}

	public void nextFind() {
		if (notePadControler.getTextBuffer().equals("")) {
			find();
		} else {
			findOperation();
		}
	}

	public void replace() {
		replaceDialog = new ReplaceDialog(NotePadView.this);
	}

	public void selectAll() {
		taMain.selectAll();
	}

	// 찾기 작업
	public void findOperation() {
		String content = "";
		String findStr = "";
		int startIdx = 0, endIdx = 0, fromIdx = 0;

		NotePadView.this.taMain.requestFocus();

		findStr = notePadControler.getTextBuffer();
		content = NotePadView.this.taMain.getText();

		fromIdx = taMain.getCaretPosition();
		startIdx = content.indexOf(findStr, fromIdx);
		endIdx = startIdx + findStr.length();

		if (startIdx == -1) {
			NotePadView.this.taMain.select(0, -1);
			fromIdx = 0;
			startIdx = 0;
			endIdx = 0;
			// /tFindText.setText("문서끝입니다~");
		} else {
			NotePadView.this.taMain.select(startIdx, endIdx);
		}
		System.out.println(startIdx + "," + endIdx);
	}

	// 바꾸기 작업
	public void replaceOperation() {
		if (taMain.getSelectionEnd() - taMain.getSelectionStart() != 0)
			replaceRange(notePadControler.getReplaceTextBuffer());
		taMain.select(taMain.getCaretPosition()
				- notePadControler.getReplaceTextBuffer().length(),
				taMain.getCaretPosition());

	}

	// 모두 바꾸기작업
	public void replaceAllOperation() {

		do {
			taMain.setCaretPosition(0);
			findOperation();
			replaceOperation();
		} while (taMain.getSelectionEnd() - taMain.getSelectionStart() != 0);
	}

	// 모두 바꾸기작업에 쓰이는 메소드
	public void replaceRange(String replaceString) {
		taMain.replaceRange(replaceString, taMain.getSelectionStart(),
				taMain.getSelectionEnd());
	}

	// 서식메뉴 -폰트 바꾸기
	public void fontStyle() {
		fontStyleView = new FontStyleView();
		fontStyleView.setBounds(400, 500, 400, 350);
		fontStyleView.setVisible(true);
	}

	// 찾기메뉴 내부클래스
	class FindDialog extends JDialog implements ActionListener {

		JTextField tFindText;
		JButton bFind, bClose;

		FindDialog(NotePadView notePadView) {
			super(notePadView, "찾기", false);
			setLayout(new FlowLayout());
			add(tFindText = new JTextField(15));
			add(bFind = new JButton("찾기"));
			add(bClose = new JButton("닫기"));
			bFind.addActionListener(this);
			bClose.addActionListener(this);
			tFindText.setText(notePadControler.getTextBuffer());
			setBounds(650, 450, 250, 100);
			setResizable(false);
			setVisible(true);
			// 찾기 작업중 찾는문자열 필드 문자가 사라지는 현상을 방지하기 위해 리스너를 붙임
			tFindText.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent arg0) {
					notePadControler.setTextBuffer(tFindText.getText());
				}
			});
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("찾기")) {
				tFindText.setText(notePadControler.getTextBuffer());
				findOperation();
			} else {
				this.dispose();
			}
		}

	}

	// 바꾸기 메뉴 내부클래스
	class ReplaceDialog extends JDialog implements ActionListener {

		JTextField tFindText, tReplaceText;
		JButton bFind, bReplace, bReplaceAll, bClose;

		ReplaceDialog(NotePadView notePadView) {
			super(notePadView, "바꾸기", false);
			setLayout(new FlowLayout());
			add(tFindText = new JTextField(15));
			add(bFind = new JButton("찾기"));
			add(tReplaceText = new JTextField(15));
			add(bReplace = new JButton("바꾸기"));
			add(bReplaceAll = new JButton("모두바꾸기"));
			add(bClose = new JButton("닫기"));
			bFind.addActionListener(this);
			bReplace.addActionListener(this);
			bReplaceAll.addActionListener(this);
			bClose.addActionListener(this);

			tFindText.setText(notePadControler.getTextBuffer());
			notePadControler.setReplaceTextBuffer("");
			setBounds(650, 450, 300, 130);
			setResizable(false);
			setVisible(true);
			// 바꾸기 작업중 필드간에 커서 이동시 글자가 사라지는 현상을 방지하기 위해 4개의 리스너를 부착
			tFindText.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent arg0) {
					notePadControler.setTextBuffer(tFindText.getText());
				}
			});
			tReplaceText.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent arg0) {
					notePadControler.setReplaceTextBuffer(tReplaceText
							.getText());
				}
			});
			tFindText.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent arg0) {
					tFindText.setText(notePadControler.getTextBuffer());
				}
			});
			tReplaceText.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent arg0) {
					tReplaceText.setText(notePadControler
							.getReplaceTextBuffer());
				}
			});
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("찾기")) {
				tFindText.setText(notePadControler.getTextBuffer());
				findOperation();
			} else if (e.getActionCommand().equals("바꾸기")) {
				tFindText.setText(notePadControler.getTextBuffer());
				tReplaceText.setText(notePadControler.getReplaceTextBuffer());
				replaceOperation();
			} else if (e.getActionCommand().equals("모두바꾸기")) {
				tFindText.setText(notePadControler.getTextBuffer());
				tReplaceText.setText(notePadControler.getReplaceTextBuffer());
				replaceAllOperation();
			} else {
				this.dispose();
			}
		}

	}

	class FileMenuView extends JFrame {
		String type;
		JFileChooser fileChooser;
		JLabel l;
		String str = "";
		int c;
		int menuType;

		FileMenuView(String type) {
			this.type = type;
			fileChooser = new JFileChooser(".");
			if (type.equals("open")) {
				menuType = fileChooser.showOpenDialog(this);
				this.setTitle("열기");
			} else {
				menuType = fileChooser.showSaveDialog(this);
				this.setTitle("저장");
			}
			try {
				File file = fileChooser.getSelectedFile();
				String path = file.getPath();

				if (menuType == JFileChooser.APPROVE_OPTION) {
					if (type.equals("open")) {
						if (file != null) {
							BufferedReader in = new BufferedReader(
									new FileReader(path));
							while ((c = in.read()) >= 0) {
								str += (char) c;
							}
							taMain.setText(str);
							in.close();
						}
					} else if (type.equals("save")) {
						if (file != null) {
							BufferedWriter out = new BufferedWriter(
									new FileWriter(path));
							out.write(taMain.getText());
							out.close();
						}

					}
				}
			} catch (Exception e) {
				// System.out.println("열기나  저장중  캔슬  햇죠?");
			}
		}
	}

	class FontStyleView extends JFrame implements ActionListener,
			ListSelectionListener {
		String[] fontName = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		String[] fontStyleName = { "PLAIN", "BOLD", "ITALIC" };
		String[] fontSize = { "6", "7", "8", "9", "10", "12", "14", "17", "20",
				"24", "30", "40" };

		JList listFontName, listFontStyle, listFontSize;
		JPanel listPanel, centerPanel, southPanel;
		JScrollPane listScrollPane;
		JLabel textLabel;
		JButton bConfirm, bCancel;

		Font newFont = null;

		public FontStyleView() {
			// 배치관리자
			Container con = getContentPane();
			centerPanel = new JPanel(new GridLayout(2, 1));
			listPanel = new JPanel();
			listPanel.setLayout(new GridLayout(0, 3));

			// 폰트 네임 부분
			listFontName = new JList(fontName);
			listFontName.addListSelectionListener(this);
			listScrollPane = new JScrollPane(listFontName);
			listScrollPane.setBorder(new TitledBorder("Font  Name"));
			listPanel.add(listScrollPane);
			// 리스트는 하나만 선택가능하고, 필드의 값을 가져와 해당 폰트 이름에 기본적으로 셀렉트 되어잇음
			listFontName
					.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listFontName.setSelectedValue(taMain.getFont().getName(), false);

			// 폰트 스타일 부분
			listFontStyle = new JList(fontStyleName);
			listFontStyle.addListSelectionListener(this);
			listScrollPane = new JScrollPane(listFontStyle);
			listScrollPane.setBorder(new TitledBorder("Font  Style  Name"));
			listPanel.add(listScrollPane);
			// 리스트는 하나만 선택가능하고, 필드의 값을 가져와 해당 폰트 스타일이름에 기본적으로 셀렉트 되어잇음
			listFontStyle
					.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listFontStyle.setSelectedIndex(taMain.getFont().getStyle());

			// 폰트 사이즈 부분//
			listFontSize = new JList(fontSize);
			listFontSize.addListSelectionListener(this);
			listScrollPane = new JScrollPane(listFontSize);
			listScrollPane.setBorder(new TitledBorder("Font  Size"));
			listPanel.add(listScrollPane);
			// 리스트는 하나만 선택가능하고, 필드의 값을 가져와 해당 폰트 사이즈에 기본적으로 셀렉트 되어잇음
			listFontSize
					.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listFontSize.setSelectedValue("" + taMain.getFont().getSize(),
					false);
			// 폰트 예문 부분
			textLabel = new JLabel("Hello~  Andromeda");
			textLabel.setHorizontalAlignment(JLabel.CENTER);
			// 예문 폰트는 현재 메모장필드에 세팅된 폰트값을 가져와서 세팅됨
			textLabel
					.setFont(new Font(
							(String) (listFontName.getSelectedValue()),
							listFontStyle.getSelectedIndex(), Integer
									.parseInt((String) (listFontSize
											.getSelectedValue()))));
			;

			centerPanel.add(listPanel);
			centerPanel.add(textLabel);

			bConfirm = new JButton("확인");
			bCancel = new JButton("취소");
			bConfirm.addActionListener(this);
			bCancel.addActionListener(this);
			southPanel = new JPanel();
			southPanel.add(bConfirm);
			southPanel.add(bCancel);

			con.add(centerPanel, "Center");
			con.add(southPanel, "South");

			newFont = textLabel.getFont();
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("확인"))
				taMain.setFont(newFont);
			this.dispose();
		}

		// 리스트를 고를때마다 예문 폰트가 바뀜
		public void valueChanged(ListSelectionEvent arg0) {
			try {
				textLabel.setFont(new Font((String) (listFontName
						.getSelectedValue()), listFontStyle.getSelectedIndex(),
						Integer.parseInt((String) (listFontSize
								.getSelectedValue()))));
				newFont = textLabel.getFont();
			} catch (NullPointerException e) {
				// System.out.println("폰트  처음  눌럿을때  나는  에러  무시해도됨  ");
			}

		}
	}
}
