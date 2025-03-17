"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2, ArrowLeft } from "lucide-react";
import Link from "next/link";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { toast } from "sonner";

const forgotPasswordSchema = z.object({
    email: z.string().email({ message: "Please enter a valid email address" }),
});

type ForgotPasswordFormValues = z.infer<typeof forgotPasswordSchema>;

export default function ForgotPasswordComponent() {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);

    const form = useForm<ForgotPasswordFormValues>({
        resolver: zodResolver(forgotPasswordSchema),
        defaultValues: {
            email: "",
        },
    });

    const mockResetPasswordApi = async (email: string): Promise<{ success: boolean; message: string }> => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    success: true,
                    message: "Password reset link sent to your email"
                });
            }, 1500);
        });
    };

    const onSubmit = async (data: ForgotPasswordFormValues) => {
        setIsLoading(true);
        try {
            const result = await mockResetPasswordApi(data.email);

            if (result.success) {
                toast.success(result.message);
                form.reset();
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
                    <CardTitle className="text-3xl font-bold text-center">Forgot Password</CardTitle>
                    <CardDescription className="text-center">
                        Enter your email address and we'll send you a link to reset your password
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
                            <Button type="submit" className="w-full" disabled={isLoading}>
                                {isLoading ? (
                                    <>
                                        <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                                        Sending...
                                    </>
                                ) : (
                                    "Send Reset Link"
                                )}
                            </Button>
                        </form>
                    </Form>
                </CardContent>
                <CardFooter className="flex flex-col space-y-4 pt-0">
                    <div className="text-center text-sm">
                        <Link href="/login" className="inline-flex items-center text-sm font-medium text-primary hover:underline">
                            <ArrowLeft className="mr-1 h-4 w-4" />
                            Back to login
                        </Link>
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
}