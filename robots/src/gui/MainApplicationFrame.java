package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JOptionPane;

import log.Logger;


public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    /**
     * Что требуется сделать:
     * 1. Метод создания меню перегружен функционалом и трудно читается.
     * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
     *
     */
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createExitMenu());
        menuBar.add(createDocumentMenu());
        menuBar.add(createLookAndFeelMenu());
        menuBar.add(createTestMenu());

        return menuBar;
    }

    private JMenu createExitMenu() {
        JMenu exitMenu = new JMenu("Выход");
        exitMenu.setMnemonic(KeyEvent.VK_X);

        exitMenu.add(createExitMenuItem());

        return exitMenu;
    }

    private JMenuItem createExitMenuItem() {
        JMenuItem exitItem = new JMenuItem("Закрыть", KeyEvent.VK_Q);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        exitItem.addActionListener((event) -> confirmExit());

        return exitItem;
    }

    private void confirmExit() {
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        int response = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите выйти?",
                "Подтверждение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private JMenu createDocumentMenu()
    {
        JMenu documentMenu = new JMenu("Документ");
        documentMenu.setMnemonic(KeyEvent.VK_D);

        documentMenu.add(createNewMenuItem());

        return documentMenu;
    }

    private JMenuItem createNewMenuItem()
    {
        JMenuItem newItem = new JMenuItem("Новый", KeyEvent.VK_N);
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));

        return newItem;
    }

    private JMenu createLookAndFeelMenu()
    {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);

        lookAndFeelMenu.add(createLookAndFeelMenuItem("Системная схема", UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(createLookAndFeelMenuItem("Универсальная схема", UIManager.getCrossPlatformLookAndFeelClassName()));

        return lookAndFeelMenu;
    }

    private JMenuItem createLookAndFeelMenuItem(String title, String className)
    {
        JMenuItem menuItem = new JMenuItem(title);

        menuItem.addActionListener((event) -> {
            setLookAndFeel(className);
            this.invalidate();
            SwingUtilities.updateComponentTreeUI(this); // Обновление интерфейса
            Logger.debug("Изменен режим отображения на: " + title);
        });

        return menuItem;
    }

    private JMenu createTestMenu()
    {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);

        testMenu.add(createTestLogMessageMenuItem());

        return testMenu;
    }

    private JMenuItem createTestLogMessageMenuItem()
    {
        JMenuItem logMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);

        logMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));

        return logMessageItem;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
