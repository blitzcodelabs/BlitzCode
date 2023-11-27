import Link from "next/link";
import Button from "../ui/Button";

const Header = () => {
  return (
    <header className="my-64 flex justify-between items-center">
      <img src="logo.svg" alt="logo" className="max-w-sm" />
      <nav className="flex gap-8">
        <Button size="quarter" asChild>
          <Link href="course">courses</Link>
        </Button>
        <Button size="quarter" asChild>
          <Link href="settings">settings</Link>
        </Button>
      </nav>
    </header>
  );
};

export default Header;
