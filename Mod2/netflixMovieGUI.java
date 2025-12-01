package Mod2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class netflixMovieGUI extends JFrame {

    // GUI components as instance variables
    private JTextField titleField;
    private JTextField genreField;
    private JTextField releaseYearField;
    private JTextField directorField;
    private JTextField mainActorField;
    private JTextField mainActressField;
    private JTextField languageField;
    private JTextField durationField;
    private JTextField imdbRatingField;
    private JTextField viewerRestrictionField;
    private JTextField seasonsField;
    private JTextField episodesField;

    private JLabel seasonsLabel;
    private JLabel episodesLabel;

    private JComboBox<String> contentTypeComboBox;
    private JTextArea movieOutputArea;

    public netflixMovieGUI() {
        setTitle("Netflix Movie Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null); // Center the window

        // Use BorderLayout to organize sections
        setLayout(new BorderLayout(10, 10));

        // Create and add panels
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        JScrollPane outputPanel = createOutputPanel();

        add(formPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Enter Movie / Series Information"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Content Type (Movie / Series)
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Content Type:"), gbc);

        contentTypeComboBox = new JComboBox<>(new String[] { "Movie", "Series" });
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(contentTypeComboBox, gbc);

        row++;

        // Title
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Movie Title:"), gbc);

        titleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(titleField, gbc);

        row++;

        // Genre
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Genre:"), gbc);

        genreField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(genreField, gbc);

        row++;

        // Release Year
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Release Year:"), gbc);

        releaseYearField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(releaseYearField, gbc);

        row++;

        // Director
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Director:"), gbc);

        directorField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(directorField, gbc);

        row++;

        // Main Actor
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Main Actor:"), gbc);

        mainActorField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(mainActorField, gbc);

        row++;

        // Main Actress
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Main Actress:"), gbc);

        mainActressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(mainActressField, gbc);

        row++;

        // Language
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Language:"), gbc);

        languageField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(languageField, gbc);

        row++;

        // Duration
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Duration (minutes):"), gbc);

        durationField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(durationField, gbc);

        row++;

        // IMDb Rating
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("IMDb Rating (0.0 - 10.0):"), gbc);

        imdbRatingField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(imdbRatingField, gbc);

        row++;

        // Viewer Age Restriction
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Viewer Age Restriction:"), gbc);

        viewerRestrictionField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(viewerRestrictionField, gbc);

        row++;

        // Number of Seasons (Series only)
        gbc.gridx = 0;
        gbc.gridy = row;
        seasonsLabel = new JLabel("Number of Seasons:");
        panel.add(seasonsLabel, gbc);

        seasonsField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(seasonsField, gbc);

        row++;

        // Number of Episodes (Series only)
        gbc.gridx = 0;
        gbc.gridy = row;
        episodesLabel = new JLabel("Number of Episodes:");
        panel.add(episodesLabel, gbc);

        episodesField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(episodesField, gbc);

        // Initially hide the series-specific fields
        updateSeriesFieldsVisibility();

        // Add listener to combo box to show/hide series fields
        contentTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSeriesFieldsVisibility();
                panel.revalidate();
                panel.repaint();
            }
        });

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");

        // Submit Button Action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        // Clear Button Action
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleClear();
            }
        });

        panel.add(submitButton);
        panel.add(clearButton);

        return panel;
    }

    private JScrollPane createOutputPanel() {
        movieOutputArea = new JTextArea(10, 50);
        movieOutputArea.setEditable(false);
        movieOutputArea.setLineWrap(true);
        movieOutputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(movieOutputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Movie / Series Details"));

        return scrollPane;
    }

    private void updateSeriesFieldsVisibility() {
        boolean isSeries = "Series".equals(contentTypeComboBox.getSelectedItem());

        seasonsLabel.setVisible(isSeries);
        seasonsField.setVisible(isSeries);
        episodesLabel.setVisible(isSeries);
        episodesField.setVisible(isSeries);
    }

    private void handleSubmit() {
        String contentType = (String) contentTypeComboBox.getSelectedItem();
        String title = titleField.getText().trim();
        String genre = genreField.getText().trim();
        String releaseYearText = releaseYearField.getText().trim();
        String director = directorField.getText().trim();
        String mainActor = mainActorField.getText().trim();
        String mainActress = mainActressField.getText().trim();
        String language = languageField.getText().trim();
        String durationText = durationField.getText().trim();
        String imdbRatingText = imdbRatingField.getText().trim();
        String viewerRestrictionText = viewerRestrictionField.getText().trim();
        String seasonsText = seasonsField.getText().trim();
        String episodesText = episodesField.getText().trim();

        StringBuilder errorMessages = new StringBuilder();

        // Basic non-empty validation
        if (title.isEmpty()) errorMessages.append("Movie Title cannot be empty.\n");
        if (genre.isEmpty()) errorMessages.append("Genre cannot be empty.\n");
        if (director.isEmpty()) errorMessages.append("Director cannot be empty.\n");
        if (mainActor.isEmpty()) errorMessages.append("Main Actor cannot be empty.\n");
        if (mainActress.isEmpty()) errorMessages.append("Main Actress cannot be empty.\n");
        if (language.isEmpty()) errorMessages.append("Language cannot be empty.\n");

        int releaseYear = 0;
        int duration = 0;
        double imdbRating = 0.0;
        int viewerRestriction = 0;
        int seasons = 0;
        int episodes = 0;

        // Numeric validations

        // Release Year
        if (releaseYearText.isEmpty()) {
            errorMessages.append("Release Year cannot be empty.\n");
        } else {
            try {
                releaseYear = Integer.parseInt(releaseYearText);
            } catch (NumberFormatException ex) {
                errorMessages.append("Release Year must be a number.\n");
            }
        }

        // Duration
        if (durationText.isEmpty()) {
            errorMessages.append("Duration cannot be empty.\n");
        } else {
            try {
                duration = Integer.parseInt(durationText);
                if (duration <= 0) {
                    errorMessages.append("Duration must be a positive number.\n");
                }
            } catch (NumberFormatException ex) {
                errorMessages.append("Duration must be a number.\n");
            }
        }

        // IMDb Rating
        if (imdbRatingText.isEmpty()) {
            errorMessages.append("IMDb Rating cannot be empty.\n");
        } else {
            try {
                imdbRating = Double.parseDouble(imdbRatingText);
                if (imdbRating < 0.0 || imdbRating > 10.0) {
                    errorMessages.append("IMDb Rating must be between 0.0 and 10.0.\n");
                }
            } catch (NumberFormatException ex) {
                errorMessages.append("IMDb Rating must be a number.\n");
            }
        }

        // Viewer Age Restriction
        if (viewerRestrictionText.isEmpty()) {
            errorMessages.append("Viewer Age Restriction cannot be empty.\n");
        } else {
            try {
                viewerRestriction = Integer.parseInt(viewerRestrictionText);
                if (viewerRestriction <= 0) {
                    errorMessages.append("Viewer Age Restriction must be a positive number.\n");
                }
            } catch (NumberFormatException ex) {
                errorMessages.append("Viewer Age Restriction must be a number.\n");
            }
        }

        // Series-specific validation
        boolean isSeries = "Series".equals(contentType);
        if (isSeries) {
            if (seasonsText.isEmpty()) {
                errorMessages.append("Number of Seasons cannot be empty for a Series.\n");
            } else {
                try {
                    seasons = Integer.parseInt(seasonsText);
                    if (seasons <= 0) {
                        errorMessages.append("Number of Seasons must be a positive number.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessages.append("Number of Seasons must be a number.\n");
                }
            }

            if (episodesText.isEmpty()) {
                errorMessages.append("Number of Episodes cannot be empty for a Series.\n");
            } else {
                try {
                    episodes = Integer.parseInt(episodesText);
                    if (episodes <= 0) {
                        errorMessages.append("Number of Episodes must be a positive number.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessages.append("Number of Episodes must be a number.\n");
                }
            }
        }

        // If there are any errors, show them and stop
        if (errorMessages.length() > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    errorMessages.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Build output
        StringBuilder output = new StringBuilder();
        output.append("Movie Details:\n\n");
        output.append("Content Type: ").append(contentType).append("\n");
        output.append("Title: ").append(title).append("\n");
        output.append("Genre: ").append(genre).append("\n");
        output.append("Release Year: ").append(releaseYear).append("\n");
        output.append("Director: ").append(director).append("\n");
        output.append("Main Actor: ").append(mainActor).append("\n");
        output.append("Main Actress: ").append(mainActress).append("\n");
        output.append("Language: ").append(language).append("\n");
        output.append("Duration: ").append(duration).append(" minutes\n");
        output.append("IMDb Rating: ").append(imdbRating).append("\n");
        output.append("Viewer Restriction: ").append(viewerRestriction).append("+\n");

        if (isSeries) {
            output.append("Number of Seasons: ").append(seasons).append("\n");
            output.append("Number of Episodes: ").append(episodes).append("\n");
        }

        movieOutputArea.setText(output.toString());
    }

    private void handleClear() {
        titleField.setText("");
        genreField.setText("");
        releaseYearField.setText("");
        directorField.setText("");
        mainActorField.setText("");
        mainActressField.setText("");
        languageField.setText("");
        durationField.setText("");
        imdbRatingField.setText("");
        viewerRestrictionField.setText("");
        seasonsField.setText("");
        episodesField.setText("");
        movieOutputArea.setText("");
        contentTypeComboBox.setSelectedItem("Movie");
        updateSeriesFieldsVisibility();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                netflixMovieGUI gui = new netflixMovieGUI();
                gui.setVisible(true);
            }
        });
    }
}

