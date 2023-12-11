import type { Metadata } from "next";
import { Rubik, Space_Mono } from "next/font/google";
import ReactQueryProvider from "@/providers/ReactQueryProvider";

import "./globals.css";

const rubik = Rubik({
  subsets: ["latin"],
  weight: "800",
  variable: "--font-rubik",
});

const space_mono = Space_Mono({
  subsets: ["latin"],
  variable: "--font-space-mono",
  weight: "400"
});

export const metadata: Metadata = {
  title: "BlitzCode",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
      <html lang="en">
        <body className={`${rubik.variable} ${space_mono.variable}`}>
          <ReactQueryProvider>{children}</ReactQueryProvider>
        </body>
      </html>
  );
}
