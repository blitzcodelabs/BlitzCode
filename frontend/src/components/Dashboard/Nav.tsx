import Link from "next/link";
import Button from "../ui/Button";

const Nav = () => {
  return (
    <nav className="my-64 flex justify-between items-center">
      <img src="logo.svg" alt="logo" className="max-w-sm" />
      <div className="flex gap-8">
        <Button size="quarter">courses</Button>
        <Button size="quarter" asChild>
          <Link href="settings">settings</Link>
        </Button>
      </div>
    </nav>
  );
};

export default Nav;
