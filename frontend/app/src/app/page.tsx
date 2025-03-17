"use client";
import { useEffect, useState } from "react";

export default function Home() {
    const [randomImage, setRandomImage] = useState("https://images.unsplash.com/photo-1535463731090-e34f4b5098c5?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fG5hdHVyYWx8ZW58MHx8MHx8fDA%3D");

    useEffect(() => {
        const seed = new Date().getDate(); // Changes daily
        setRandomImage(`https://images.unsplash.com/photo-1535463731090-e34f4b5098c5?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fG5hdHVyYWx8ZW58MHx8MHx8fDA%3D`);
    }, []);

    return (
        <div className="flex min-h-screen">
            <div className="w-1/2 flex items-center justify-center bg-gradient-to-br from-slate-100 to-slate-200 dark:from-slate-900 dark:to-slate-800">
                <div className="w-full max-w-md px-8">
                    <div className="text-center mb-6">
                        <h1 className="text-3xl font-bold">Welcome Back</h1>
                        <p className="text-muted-foreground">Enter your credentials to sign in</p>
                    </div>

                    <form className="space-y-4">
                        <div className="space-y-2">
                            <label className="text-sm font-medium" htmlFor="email">Email</label>
                            <input
                                id="email"
                                type="email"
                                placeholder="name@example.com"
                                className="w-full p-2 border rounded-md"
                            />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium" htmlFor="password">Password</label>
                            <input
                                id="password"
                                type="password"
                                placeholder="••••••••"
                                className="w-full p-2 border rounded-md"
                            />
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 rounded-md"
                        >
                            Sign In
                        </button>
                    </form>

                    <div className="mt-6 text-center text-sm">
                        <a href="#" className="text-blue-600 hover:underline">Forgot password?</a>
                        <div className="mt-2">
                            Don't have an account? <a href="#" className="text-blue-600 hover:underline">Sign up</a>
                        </div>
                    </div>
                </div>
            </div>

            <div className="w-1/2 relative">
                {randomImage && (
                    <>
                        <img
                            src={randomImage}
                            alt="Decorative background"
                            className="h-full w-full object-cover"
                        />
                        <div className="absolute inset-0 bg-black/30 flex flex-col items-center justify-center text-white">
                            <h2 className="text-4xl font-bold mb-4">Welcome to Online Shopping</h2>
                            <p className="text-xl">Shop as a Trillionaire</p>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}