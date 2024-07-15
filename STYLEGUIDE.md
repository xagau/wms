# Style Guide

## Light Theme

- primaryColor: #2196F3 !default;
- primaryLightColor: scale-color($primaryColor, $lightness: 60%) !default;
- primaryDarkColor: scale-color($primaryColor, $lightness: -10%) !default;
- primaryDarkerColor: scale-color($primaryColor, $lightness: -20%) !default;
- primaryTextColor: #ffffff !default;

- highlightBg: #E3F2FD !default;
- highlightTextColor: #495057 !default;

- @import '../sass/theme/_theme_light';

## Dim Theme

- primaryColor: #90CAF9 !default;
- primaryLightColor: scale-color($primaryColor, $lightness: 30%) !default;
- primaryDarkColor: scale-color($primaryColor, $lightness: -10%) !default;
- primaryDarkerColor: scale-color($primaryColor, $lightness: -20%) !default;
- primaryTextColor: #212529 !default;

- highlightBg: rgba(144, 202, 249, 0.16) !default;
- highlightTextColor: rgba(255,255,255,.87) !default;

- @import '../sass/theme/_theme_dim';

## Dark Theme

- primaryColor: #90CAF9 !default;
- primaryLightColor: scale-color($primaryColor, $lightness: 30%) !default;
- primaryDarkColor: scale-color($primaryColor, $lightness: -10%) !default;
- primaryDarkerColor: scale-color($primaryColor, $lightness: -20%) !default;
- primaryTextColor: #212529 !default;

- $highlightBg: rgba(144, 202, 249, 0.16) !default;
- $highlightTextColor: rgba(255,255,255,.87) !default;

- @import '../sass/theme/_theme_dark';

## Typography

### Headings

We use the following heading styles:

- H1: Used for page titles, should be used sparingly
- H2: Used for section headings
- H3: Used for subsection headings
- H4: Used for sub-subsection headings

### Body Text


- Font Family: "Nunito",-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji" !default;
- font-weight: 600;
- font-size: 22px;
- Color: #ffffff;

## Buttons

We use the following button styles:

### Light Theme
  - Primary:#2196F3; 
  - Secondary: #607D8B; 
  - Success: #689F38;
  - Info: #0288D1;
  - Warning: #FBC02D;
  - Help: #9C27B0;
  - Danger: #D32F2F;
### Dim Theme
  - Primary: #90CAF9; 
  - Secondary: #78909C; 
  - Success: #C5E1A5;
  - Info: #81D4FA;
  - Warning: #FFE082;
  - Help: #CE93D8;
  - Danger: #F48FB1;
### Dark Theme
  - Primary: #90CAF9; 
  - Secondary: #78909C; 
  - Success: #C5E1A5;
  - Info: #81D4FA;
  - Warning: #FFE082;
  - Help: #CE93D8;
  - Danger: #F48FB1;

## Forms

We use the following form styles:

- Labels: Bold, black (#000000)
- Input fields: 1px solid gray (#cccccc)
- Submit button: Same style as primary button

## Icons

We use PrimeIcons library, the official icons suite from PrimeTek.

## Layout

We use a responsive 12-column grid layout. The maximum width is 1200px.

## Images

Images should be optimized for web and should have a maximum width of 1280px. We prefer to use JPEG format for photographs and PNG format for graphics.
