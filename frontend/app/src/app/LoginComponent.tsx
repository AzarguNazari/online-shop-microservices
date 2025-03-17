"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Eye, EyeOff, Loader2 } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { toast } from "sonner";

const loginSchema = z.object({
    email: z.string().email({ message: "Please enter a valid email address" }),
    password: z.string().min(6, { message: "Password must be at least 6 characters" }),
});

// Type for the form
type LoginFormValues = z.infer<typeof loginSchema>;

export default function LoginComponent() {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    // Initialize form with react-hook-form and zod validation
    const form = useForm<LoginFormValues>({
        resolver: zodResolver(loginSchema),
        defaultValues: {
            email: "",
            password: "",
        },
    });

    // Mock API login function
    const mockLoginApi = async (email: string, password: string): Promise<{ success: boolean; message: string }> => {
        // Simulate API delay
        return new Promise((resolve) => {
            setTimeout(() => {
                // For demo purposes: login succeeds if password includes "pass"
                if (password.toLowerCase().includes("pass")) {
                    resolve({ success: true, message: "Login successful" });
                } else {
                    resolve({ success: false, message: "Invalid email or password" });
                }
            }, 1500);
        });
    };

    // Handle form submission
    const onSubmit = async (data: LoginFormValues) => {
        setIsLoading(true);
        try {
            const result = await mockLoginApi(data.email, data.password);

            if (result.success) {
                toast.success(result.message);
                // Redirect to dashboard after successful login
                router.push("/dashboard");
            } else {
                toast.error(result.message);
            }
        } catch (error) {
            toast.error("An unexpected error occurred. Please try again.");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gradient-to-br from-slate-100 to-slate-200 dark:from-slate-900 dark:to-slate-800">
            <Card className="w-full max-w-md shadow-xl border-0 dark:bg-slate-950">
                <CardHeader className="space-y-1">
                    <CardTitle className="text-3xl font-bold text-center">Welcome Back</CardTitle>
                    <CardDescription className="text-center">
                        Enter your credentials to sign in to your account
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <Form {...form}>
                        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                            <FormField
                                control={form.control}
                                name="email"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Email</FormLabel>
                                        <FormControl>
                                            <Input
                                                placeholder="name@example.com"
                                                type="email"
                                                autoComplete="email"
                                                disabled={isLoading}
                                                {...field}
                                            />
                                        </FormControl>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="password"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Password</FormLabel>
                                        <FormControl>
                                            <div className="relative">
                                                <Input
                                                    placeholder="••••••••"
                                                    type={showPassword ? "text" : "password"}
                                                    autoComplete="current-password"
                                                    disabled={isLoading}
                                                    {...field}
                                                />
                                                <Button
                                                    type="button"
                                                    variant="ghost"
                                                    size="sm"
                                                    className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
                                                    onClick={() => setShowPassword(!showPassword)}
                                                    disabled={isLoading}
                                                >
                                                    {showPassword ? (
                                                        <EyeOff className="h-4 w-4" />
                                                    ) : (
                                                        <Eye className="h-4 w-4" />
                                                    )}
                                                    <span className="sr-only">
                            {showPassword ? "Hide password" : "Show password"}
                          </span>
                                                </Button>
                                            </div>
                                        </FormControl>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                            <Button
                                type="submit"
                                className="w-full bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700"
                                disabled={isLoading}
                            >
                                {isLoading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                                {isLoading ? "Signing in..." : "Sign In"}
                            </Button>
                        </form>
                    </Form>
                </CardContent>
                <CardFooter className="flex flex-col space-y-4">
                    <div className="text-sm text-center text-muted-foreground">
                        <a href="#" className="hover:text-primary underline-offset-4 underline">Forgot your password?</a>
                    </div>
                    <div className="text-sm text-center text-muted-foreground">
                        Don't have an account?{" "}
                        <a href="#" className="hover:text-primary underline-offset-4 underline">Create an account</a>
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
}