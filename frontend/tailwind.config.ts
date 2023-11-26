import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    colors: {
      transparent: "transparent",
      current: "currentColor",
      background: "#fff",
      primary: "#B784FF",
      secondary: "#4F3E98",
      accent: "#5DDEE8",
      success: "#5FBD48",
      fail: "#FE6D74"
    },
    borderRadius: {
      DEFAULT: "0.5rem"
    },
    borderWidth: {
      DEFAULT: "2px"
    },
    fontFamily: {
      sans: ['var(--font-rubik)'],
      mono: ['var(--font-jetbrains-mono)'],
    },
    spacing: {
      0: "0px",
      4: "0.25rem",
      8: "0.5rem",
      16: "1rem",
      32: "2rem",
      64: "4rem",
      128: "8rem",
      256: "16rem",
      512: "32rem",
      1024: "64rem"
    },
    extend: {
      keyframes: {
        slideDown: {
          from: { height: "0" },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        slideUp: {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: "0" },
        },
      },
      animation: {
        slideDown: "slideDown 300ms cubic-bezier(0.87, 0, 0.13, 1)",
        slideUp: "slideUp 300ms cubic-bezier(0.87, 0, 0.13, 1)",
      },
    }
  },
  plugins: [],
}

export default config
