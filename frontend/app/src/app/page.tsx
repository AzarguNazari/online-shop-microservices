// src/app/page.tsx
import LoginPage from "@/app/LoginPage";

export default function Home() {
  return (
      <main className="flex min-h-screen flex-col items-center justify-center p-24">
        <div className="text-center">
          <LoginPage />
        </div>
      </main>
  )
}