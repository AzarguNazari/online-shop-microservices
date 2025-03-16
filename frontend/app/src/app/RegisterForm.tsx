// src/components/auth/register-form.tsx
"use client"

import {useState} from "react"
import {useForm} from "react-hook-form"
import {zodResolver} from "@hookform/resolvers/zod"
import * as z from "zod"
import {AlertCircle, CheckCircle, Loader2} from "lucide-react"

import {Button} from "@/components/ui/button"
import {Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage,} from "@/components/ui/form"
import {Input} from "@/components/ui/input"
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card"
import {Checkbox} from "@/components/ui/checkbox"
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select"
import {Avatar, AvatarFallback} from "@/components/ui/avatar"
import {Separator} from "@/components/ui/separator"
import {Alert, AlertDescription, AlertTitle} from "@/components/ui/alert"

const passwordSchema = z
    .string()
    .min(8, { message: "Password must be at least 8 characters" })
    .regex(/[A-Z]/, { message: "Password must contain at least one uppercase letter" })
    .regex(/[a-z]/, { message: "Password must contain at least one lowercase letter" })
    .regex(/[0-9]/, { message: "Password must contain at least one number" })
    .regex(/[^A-Za-z0-9]/, { message: "Password must contain at least one special character" })

const formSchema = z.object({
    firstName: z.string().min(2, { message: "First name must be at least 2 characters" }),
    lastName: z.string().min(2, { message: "Last name must be at least 2 characters" }),
    email: z.string().email({ message: "Please enter a valid email address" }),
    password: passwordSchema,
    confirmPassword: z.string(),
    username: z.string().min(3, { message: "Username must be at least 3 characters" })
        .regex(/^[a-zA-Z0-9_]+$/, { message: "Username can only contain letters, numbers, and underscores" }),
    role: z.string().min(1, { message: "Please select a role" }),
    acceptTerms: z.boolean().refine(val => val === true, {
        message: "You must accept the terms and conditions",
    }),
}).refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
})

export function RegisterForm() {
    const [isLoading, setIsLoading] = useState(false)
    const [step, setStep] = useState(1)
    const [registrationSuccess, setRegistrationSuccess] = useState(false)
    const [passwordStrength, setPasswordStrength] = useState(0)

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            confirmPassword: "",
            username: "",
            role: "",
            acceptTerms: false,
        },
    })

    const watchPassword = form.watch("password")

    const calculatePasswordStrength = (password: string) => {
        if (!password) return 0

        let strength = 0
        if (password.length >= 8) strength += 1
        if (/[A-Z]/.test(password)) strength += 1
        if (/[a-z]/.test(password)) strength += 1
        if (/[0-9]/.test(password)) strength += 1
        if (/[^A-Za-z0-9]/.test(password)) strength += 1

        return strength
    }

    useState(() => {
        setPasswordStrength(calculatePasswordStrength(watchPassword))
    })

    const onSubmit = async (values: z.infer<typeof formSchema>) => {
        setIsLoading(true)

        try {
            // Simulate API call
            await new Promise(resolve => setTimeout(resolve, 1500))
            console.log("Registration values:", values)
            setRegistrationSuccess(true)
        } catch (error) {
            console.error("Registration failed:", error)
        } finally {
            setIsLoading(false)
        }
    }

    const nextStep = () => {
        const fieldsToValidate = step === 1
            ? ["firstName", "lastName", "email"]
            : ["username", "password", "confirmPassword"];

        form.trigger(fieldsToValidate as any).then((isValid) => {
            if (isValid) {
                setStep(step + 1);
            }
        });
    };

    const prevStep = () => {
        setStep(step - 1);
    };

    if (registrationSuccess) {
        return (
            <Card className="w-full max-w-md mx-auto">
                <CardHeader>
                    <div className="flex justify-center mb-4">
                        <CheckCircle className="h-16 w-16 text-green-500" />
                    </div>
                    <CardTitle className="text-center">Registration Successful!</CardTitle>
                    <CardDescription className="text-center">
                        Your account has been created successfully
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <Alert>
                        <AlertCircle className="h-4 w-4" />
                        <AlertTitle>Verification Email Sent</AlertTitle>
                        <AlertDescription>
                            We've sent a verification email to your inbox. Please check your email and click the verification link to activate your account.
                        </AlertDescription>
                    </Alert>
                </CardContent>
                <CardFooter className="flex justify-center">
                    <Button variant="outline" onClick={() => window.location.href = "/login"}>
                        Go to Login
                    </Button>
                </CardFooter>
            </Card>
        )
    }

    return (
        <Card className="w-full max-w-md mx-auto">
            <CardHeader>
                <div className="flex justify-center mb-4">
                    <div className="relative">
                        <Avatar className="h-16 w-16">
                            <AvatarFallback>
                                {form.watch("firstName")?.[0]?.toUpperCase() || "U"}
                                {form.watch("lastName")?.[0]?.toUpperCase() || "R"}
                            </AvatarFallback>
                        </Avatar>
                    </div>
                </div>
                <CardTitle className="text-center">Create an Account</CardTitle>
                <CardDescription className="text-center">
                    Join our community and get started today
                </CardDescription>
            </CardHeader>
            <CardContent>
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                        {/* Progress indicator */}
                        <div className="mb-6">
                            <div className="flex justify-between mb-2">
                                <div className="text-sm font-medium">Step {step} of 3</div>
                                <div className="text-sm text-gray-500">
                                    {step === 1 ? "Personal Information" : step === 2 ? "Account Details" : "Review & Confirm"}
                                </div>
                            </div>
                            <div className="w-full bg-gray-200 rounded-full h-2.5">
                                <div
                                    className="bg-blue-600 h-2.5 rounded-full transition-all"
                                    style={{ width: `${(step / 3) * 100}%` }}
                                ></div>
                            </div>
                        </div>

                        {/* Step 1: Personal Information */}
                        {step === 1 && (
                            <div className="space-y-4">
                                <div className="grid grid-cols-2 gap-4">
                                    <FormField
                                        control={form.control}
                                        name="firstName"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>First Name</FormLabel>
                                                <FormControl>
                                                    <Input placeholder="John" {...field} />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                    <FormField
                                        control={form.control}
                                        name="lastName"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Last Name</FormLabel>
                                                <FormControl>
                                                    <Input placeholder="Doe" {...field} />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>
                                <FormField
                                    control={form.control}
                                    name="email"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Email</FormLabel>
                                            <FormControl>
                                                <Input type="email" placeholder="john.doe@example.com" {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="role"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Role</FormLabel>
                                            <Select onValueChange={field.onChange} defaultValue={field.value}>
                                                <FormControl>
                                                    <SelectTrigger>
                                                        <SelectValue placeholder="Select a role" />
                                                    </SelectTrigger>
                                                </FormControl>
                                                <SelectContent>
                                                    <SelectItem value="user">Regular User</SelectItem>
                                                    <SelectItem value="creator">Creator</SelectItem>
                                                    <SelectItem value="business">Business</SelectItem>
                                                </SelectContent>
                                            </Select>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                        )}

                        {/* Step 2: Account Details */}
                        {step === 2 && (
                            <div className="space-y-4">
                                <FormField
                                    control={form.control}
                                    name="username"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Username</FormLabel>
                                            <FormControl>
                                                <Input placeholder="johndoe" {...field} />
                                            </FormControl>
                                            <FormDescription>
                                                This will be your public display name.
                                            </FormDescription>
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
                                                <Input
                                                    type="password"
                                                    placeholder="Enter your password"
                                                    {...field}
                                                    onChange={(e) => {
                                                        field.onChange(e);
                                                        setPasswordStrength(calculatePasswordStrength(e.target.value));
                                                    }}
                                                />
                                            </FormControl>
                                            <div className="mt-2">
                                                <div className="text-xs mb-1">Password strength</div>
                                                <div className="w-full h-2 bg-gray-200 rounded-full overflow-hidden">
                                                    <div
                                                        className={`h-full rounded-full transition-all ${
                                                            passwordStrength <= 2 ? 'bg-red-500' :
                                                                passwordStrength <= 4 ? 'bg-yellow-500' : 'bg-green-500'
                                                        }`}
                                                        style={{ width: `${(passwordStrength / 5) * 100}%` }}
                                                    ></div>
                                                </div>
                                            </div>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="confirmPassword"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Confirm Password</FormLabel>
                                            <FormControl>
                                                <Input type="password" placeholder="Confirm your password" {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                        )}

                        {/* Step 3: Review & Confirm */}
                        {step === 3 && (
                            <div className="space-y-4">
                                <div className="bg-gray-50 p-4 rounded-md">
                                    <h3 className="font-medium mb-2">Review Your Information</h3>
                                    <div className="grid grid-cols-2 gap-y-2 text-sm">
                                        <div className="text-gray-500">First Name:</div>
                                        <div>{form.getValues("firstName")}</div>
                                        <div className="text-gray-500">Last Name:</div>
                                        <div>{form.getValues("lastName")}</div>
                                        <div className="text-gray-500">Email:</div>
                                        <div>{form.getValues("email")}</div>
                                        <div className="text-gray-500">Username:</div>
                                        <div>{form.getValues("username")}</div>
                                        <div className="text-gray-500">Role:</div>
                                        <div>{form.getValues("role") === "user" ? "Regular User" :
                                            form.getValues("role") === "creator" ? "Creator" :
                                                form.getValues("role") === "business" ? "Business" : ""}</div>
                                    </div>
                                </div>
                                <Separator />
                                <FormField
                                    control={form.control}
                                    name="acceptTerms"
                                    render={({ field }) => (
                                        <FormItem className="flex flex-row items-start space-x-3 space-y-0">
                                            <FormControl>
                                                <Checkbox
                                                    checked={field.value}
                                                    onCheckedChange={field.onChange}
                                                />
                                            </FormControl>
                                            <div className="space-y-1 leading-none">
                                                <FormLabel>
                                                    I agree to the <a href="#" className="text-blue-600 hover:underline">Terms of Service</a> and <a href="#" className="text-blue-600 hover:underline">Privacy Policy</a>
                                                </FormLabel>
                                                <FormMessage />
                                            </div>
                                        </FormItem>
                                    )}
                                />
                            </div>
                        )}

                        <div className="flex justify-between pt-4">
                            {step > 1 && (
                                <Button type="button" variant="outline" onClick={prevStep}>
                                    Back
                                </Button>
                            )}
                            {step < 3 ? (
                                <Button type="button" onClick={nextStep} className={step === 1 ? "ml-auto" : ""}>
                                    Continue
                                </Button>
                            ) : (
                                <Button type="submit" disabled={isLoading}>
                                    {isLoading ? (
                                        <>
                                            <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                                            Creating Account...
                                        </>
                                    ) : (
                                        "Create Account"
                                    )}
                                </Button>
                            )}
                        </div>
                    </form>
                </Form>
            </CardContent>
        </Card>)
}