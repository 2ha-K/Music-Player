package SoundPlayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;
import javax.sound.sampled.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import java.awt.Component;

public class player5 {
    private PlayingTimer timer;

    private boolean isPlaying = false;
    private boolean isPause = false;
    private boolean isReset = false;
    private long startTime;
    private long pauseTime;

    private DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");
    JLabel labelTimeCounter = new JLabel("00:00:00");
    JLabel labelDuration = new JLabel("00:00:00");

    // private static final int SECONDS_IN_HOUR = 60 * 60;
    // private static final int SECONDS_IN_MINUTE = 60;
    private JFrame frmMusicplayer;
    private long nowFrame;
    JLabel gg = new JLabel("testnum1");
    Clip clip;
    String thestatus;
    JComboBox list = new JComboBox();
    AudioInputStream audioStream;
    static String thePath;
    JLabel lblNewLabel = new JLabel("testnum2");
    JSlider sliderTime = new JSlider();
    JFileChooser browser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV sound", "wav");

    int returnValue;
    File seletedFile;
    String[] musics = new String[10];
    int index = 0;
    AudioInputStream ais;
    File sound;
    int counter = 0;
    int counter2 = 1;
    private int num;
    private JTextField asw;
    String ans, ans2;
    JLabel score = new JLabel();
    int point = 0;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    player5 window = new player5();
                    window.frmMusicplayer.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the application.
     * 
     * @throws InterruptedException
     */
    public player5() throws InterruptedException {
        initialize();
        /*
         * slider.setMaximum((int)clip.getMicrosecondLength()); TimerTask task = new
         * TimerTask (){ public void run() {
         * 
         * slider.setValue((int) clip.getMicrosecondPosition());
         * gg.setText(Integer.toString((int) clip.getMicrosecondPosition())); } };
         * timer.schedule (task, 500L, 500L);
         */

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        browser.setFileFilter(filter);
        frmMusicplayer = new JFrame();
        frmMusicplayer.setForeground(Color.BLACK);
        frmMusicplayer.setTitle("Music Player");
        frmMusicplayer.setBounds(100, 100, 450, 300);
        frmMusicplayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMusicplayer.getContentPane().setLayout(null);

        JButton playpause = new JButton(">=");
        playpause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
                // timer.start();
                try {// loop切換回來需多點一下play才會啟動

                    if (counter == -1) {

                        num = list.getSelectedIndex();
                        counter = 1;
                        clip.setMicrosecondPosition(nowFrame);
                        clip.start();
                        isPause = false;

                        if (counter2 == -1) {
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        } // run();//問題所在
                    } else if (counter == 1) {
                        if (clip.isRunning()) {
                            num = list.getSelectedIndex();
                            nowFrame = clip.getMicrosecondPosition();
                            clip.stop();
                            counter = -1;
                            isPause = true;

                        } else {
                            num = list.getSelectedIndex();
                            sound = new File(musics[list.getSelectedIndex()]);
                            ais = AudioSystem.getAudioInputStream(sound);
                            clip = AudioSystem.getClip();
                            clip.open(ais);
                            clip.start();
                            counter = 1;
                            isPlaying = false;

                            if (counter2 == -1) {
                                clip.loop(Clip.LOOP_CONTINUOUSLY);
                            } // run();//問題所在
                        }
                    } else if (counter == 0) {
                        num = list.getSelectedIndex();
                        sound = new File(musics[list.getSelectedIndex()]);
                        ais = AudioSystem.getAudioInputStream(sound);
                        clip = AudioSystem.getClip();
                        clip.open(ais);
                        clip.start();
                        
                        timer = new PlayingTimer(labelTimeCounter, sliderTime);
                        timer.start();

                        timer.setAudioClip(clip);
                        String length = "";
                        long hour = 0;
                        long minute = 0;
                        long second = (int) clip.getMicrosecondLength() / 1_000_000;
                        hour = second / 3600;
                        minute = (second - hour * 3600) / 60;
                        second = second - hour * 300 - minute * 60;
                        String hr = String.format("%02d", hour);
                        String min = String.format("%02d", minute);
                        String sec = String.format("%02d", second);
                        labelDuration.setText(hr + ":" + min + ":" + sec);

                        counter = 1;
                        ans = sound.getName();
                        if (counter2 == -1) {
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }

                    }

                    lblNewLabel.setText(Integer.toString(counter));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "erro");
                }

            }
        });
        playpause.setBounds(177, 146, 76, 69);
        frmMusicplayer.getContentPane().add(playpause);

        JButton next = new JButton(">");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    clip.stop();
                    num++;
                    sound = new File(musics[num]);
                    ais = AudioSystem.getAudioInputStream(sound);
                    clip = AudioSystem.getClip();
                    clip.open(ais);
                    clip.start();
                    counter = 1;
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "There is no any music");
                    num--;
                }

            }
        });
        next.setBounds(262, 153, 61, 54);
        frmMusicplayer.getContentPane().add(next);

        JButton last = new JButton("<");
        last.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    clip.stop();
                    num--;
                    sound = new File(musics[num]);
                    ais = AudioSystem.getAudioInputStream(sound);
                    clip = AudioSystem.getClip();
                    clip.open(ais);
                    clip.start();
                    counter = 1;
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "There is no any music");
                }
                num++;
            }
        });
        last.setBounds(108, 153, 61, 54);
        frmMusicplayer.getContentPane().add(last);

        JButton loop = new JButton("@test");
        loop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                counter2 = counter2 * -1;
                loop.setText(Integer.toString(counter2));
            }
        });
        loop.setBounds(333, 152, 61, 57);
        frmMusicplayer.getContentPane().add(loop);

        JButton stop = new JButton("。");
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.stop();
                counter = 0;
                isPause = false;
                isReset = true;
            }
        });
        stop.setBounds(50, 158, 48, 45);
        frmMusicplayer.getContentPane().add(stop);

        JButton add = new JButton("add");

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                returnValue = browser.showOpenDialog(add);
                if (returnValue == browser.APPROVE_OPTION) {
                    seletedFile = browser.getSelectedFile();
                    musics[index] = seletedFile.toString();// JOptionPane.showMessageDialog(null,
                                                           // seletedFile.toString());
                    list.addItem("Song." + (index + 1) + " : " + seletedFile.getName());

                    index++;
                }

            }
        });
        add.setBounds(177, 10, 87, 23);
        frmMusicplayer.getContentPane().add(add);
        list.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }// 設定換音樂
        });

        list.setBounds(50, 43, 344, 48);
        frmMusicplayer.getContentPane().add(list);

        JButton gamemod = new JButton("Play!");
        gamemod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ans2 = asw.getText();
                if (musics[list.getSelectedIndex()] != "0") {
                    if (ans2.equals(ans)) {
                        point++;
                        score.setText(Integer.toString(point) + " point");
                        // musics[list.getSelectedIndex()]="0";
                    } else if (ans == null || ans2 == null) {
                        JOptionPane.showMessageDialog(null, "You need to wright the answer or play a song.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong! Answer it again." + ans);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You already answer it.");
                }

            }
        });
        gamemod.setBounds(228, 228, 87, 23);
        frmMusicplayer.getContentPane().add(gamemod);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        lblNewLabel.setBounds(335, 232, 59, 15);
        frmMusicplayer.getContentPane().add(lblNewLabel);
        sliderTime.setEnabled(false);
        sliderTime.setAlignmentX(Component.LEFT_ALIGNMENT);

        sliderTime.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                /*
                 * clip.stop(); clip.close(); clip.setMicrosecondPosition(slider.getValue());
                 */
            }
        });
        sliderTime.setValue(0);
        sliderTime.setBounds(31, 95, 373, 26);
        frmMusicplayer.getContentPane().add(sliderTime);
        gg.setHorizontalAlignment(SwingConstants.CENTER);

        gg.setBounds(177, 121, 80, 15);
        frmMusicplayer.getContentPane().add(gg);

        asw = new JTextField();
        asw.setText(".wav");
        asw.setBounds(129, 229, 96, 21);
        frmMusicplayer.getContentPane().add(asw);
        asw.setColumns(10);
        score.setText("0 point");

        score.setBounds(82, 232, 46, 15);
        frmMusicplayer.getContentPane().add(score);

        JButton tips = new JButton("!");
        tips.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Press the song's name you heard and guess it is right or wrong!");
            }
        });
        tips.setFont(new Font("新細明體", Font.BOLD, 8));
        tips.setBounds(55, 221, 21, 26);
        frmMusicplayer.getContentPane().add(tips);

        labelTimeCounter.setBounds(41, 121, 108, 15);
        frmMusicplayer.getContentPane().add(labelTimeCounter);

        labelDuration.setBounds(343, 121, 61, 15);
        frmMusicplayer.getContentPane().add(labelDuration);
    }

    public void run() {

        startTime = System.currentTimeMillis();

        while (clip.isRunning()) {
            try {
                Thread.sleep(100);// 暫停delay時間
                if (!isPause) {
                    if (clip != null && clip.isRunning()) {
                        labelTimeCounter.setText(toTimeString());
                        int currentSecond = (int) clip.getMicrosecondPosition() / 1_000_000;
                        sliderTime.setValue(currentSecond);
                    }
                } else {
                    pauseTime += 100;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                if (isReset) {
                    sliderTime.setValue(0);
                    labelTimeCounter.setText("00:00:00");
                    break;
                }
            }
        }
    }

    private String toTimeString() {
        long now = System.currentTimeMillis();
        Date current = new Date(now - startTime - pauseTime);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeCounter = dateFormater.format(current);
        return timeCounter;
    }
}
