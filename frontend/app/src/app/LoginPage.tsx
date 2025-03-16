// src/app/login/page.tsx

import { RegisterForm } from "./RegisterForm";

export default function LoginPage() {
    return (
        <div className="min-h-screen flex items-center justify-center p-4 bg-slate-50">
            <RegisterForm />
        </div>
    )
}